# HealthApp – Datenmodell (Entwurf für Version 1)

Stand: 25.10.2026. Ergänzt `docs/PROJECT_VISION.md` (Abschnitt 17). Bei Widersprüchen gilt dieses Dokument für das Datenmodell.

## 1. Leitgedanken

- **Jede Person passt die App an die eigene Gesundheitssituation an.** Der Weg dahin: eigene *Eventdefinitionen* mit eigenen Feldern, gruppiert in eigenen *Trackables*. Vorgegebene Definitionen und Vorlagen sind nur ein Start-Set und immer änderbar.
- **Datengetrieben statt Unterklassen.** "Blutdruck" und "Kopfschmerzen" unterscheiden sich in ihren Feldern, nicht im Verhalten. Neue Messungen sind neue Daten, kein neuer Code.
- **Events und Trackables sind eigenständig.** Felder gehören zur Eventdefinition. Ein Trackable ist eine Gruppierung bzw. ein Tagebuch ohne eigene Felder.
- **Definition und Wert sind getrennt.** Definitionen beschreiben, welche Felder es gibt. Werte stehen im Event.
- **Alles optional.** Fehlt ein Wert, ist das Feld nicht erfasst. Die Schnellerfassung braucht keine Felder.
- **Ausblenden statt löschen.** Trackables, Definitionen und Begriffe werden archiviert. Die Daten bleiben abrufbar.
- **KMP-taugliche Typen.** `String`, `Long`, `Double`, `kotlin.time.Instant`. Keine Plattformklassen in `commonMain`.

## 2. Eventtypen

Fester `enum` im Code, weil Auswertungen daran hängen (z. B. Medikamenten-Einnahmetage).
Die Namen werden in der Datenbank als Text gespeichert und dürfen später nicht umbenannt werden. Anzeigetexte stehen nicht im Enum, sondern in der Oberfläche (Mehrsprachigkeit).

| Enum-Wert | Bedeutung |
|---|---|
| `SYMPTOM` | Beschwerde/Symptom |
| `MEDICATION` | Medikamenteneinnahme |
| `OBSERVATION` | Beobachtung/Veränderung |
| `EXPOSURE` | Exposition (neutraler möglicher Einflussfaktor) |
| `MEASUREMENT` | Messwert |
| `BODILY_PROCESS` | Körperlicher Vorgang (z. B. Menstruation) |
| `ACTIVITY` | Aktivität/Belastung |
| `SLEEP_RECOVERY` | Schlaf/Erholung |
| `GENERAL` | Allgemeiner, nicht weiter spezifizierter Eintrag |

## 3. Tabellen

| Tabelle | Spalten | Hinweis |
|---|---|---|
| `QuickNote` (vorhanden) | `id`, `text`, `enteredAt`, `updatedAt` | Freie Kurzeingabe. Wird bei der Zuordnung gelöscht. |
| `EventDefinition` | `id`, `name`, `eventType`, `fieldsJson`, `isPredefined`, `archivedAt?`, `updatedAt` | Der Katalog. Liefert Typ und Felder. |
| `TermAssignment` | `term` (normalisiert), `definitionId`, `useCount`, `lastUsedAt` | Primärschlüssel (`term`, `definitionId`). Lernt "Text → Definition". |
| `Event` | `id`, `definitionId`, `occurredAt`, `recordedAt`, `durationMinutes?`, `valuesJson`, `updatedAt` | Ein erfasstes Ereignis. Dauer gilt für alle Typen. |
| `Trackable` | `id`, `name`, `isSystem`, `archivedAt?`, `updatedAt` | "Sonstiges" hat `isSystem = true`, ist nicht archivierbar und nicht löschbar. |
| `EventTrackable` | `eventId`, `trackableId` | Verknüpfung (n:m). Primärschlüssel (`eventId`, `trackableId`). |

Regeln, die der Code prüft (nicht die Datenbank):
- Ein Event gehört immer zu mindestens einem Trackable (Fallback "Sonstiges").
- Die Umwandlung QuickNote → Event läuft in einer Transaktion: Definition bestimmen, Event anlegen, Trackables verknüpfen, Zuordnung lernen, QuickNote löschen.

## 4. Zuordnung von Begriffen (QuickNote → Definition)

Beim Zuordnen wird der Text der QuickNote normalisiert (Kleinschreibung, Leerzeichen bereinigt) und in `TermAssignment` gesucht:

- **Eindeutig** (genau eine Definition): wird automatisch vorgeschlagen, inklusive Typ und Feldern.
- **Mehrdeutig** (mehrere): Auswahl anbieten, nichts erzwingen.
- **Unbekannt** (keine): nicht klassifizieren. Es entsteht eine Definition vom Typ `GENERAL` ohne Felder, die später umklassifiziert werden kann.
- **Lernen:** Wählt die Nutzerin eine Definition, wird die Zuordnung gespeichert oder `useCount` erhöht. Änderungen der Zuordnung sind jederzeit möglich.

## 5. Felder

Felddefinitionen stehen als JSON in `EventDefinition.fieldsJson` (geordnete Liste). Werte stehen in `Event.valuesJson` als Zuordnung von **Feld-ID** zu Wert. Jedes Feld hat eine stabile ID. Umbenennen verändert alte Werte nicht.

Gemeinsame Eigenschaften einer Felddefinition: `id`, `label`, Art mit Konfiguration. Alle Felder sind optional.

| Feldart | Deckt ab | Wert | Stufe |
|---|---|---|---|
| `Text` | Freitext | Text | 1 |
| `Terms` | Autocomplete-Chips mit eigenen Begriffen | Liste von Begriffen | 1 |
| `Number` (Nachkommastellen, Einheit?, min?, max?, Zähler?) | Ganzzahl, Dezimalzahl, Zahl mit Einheit, Zählwert | Zahl (`Double`) | 1 |
| `Scale` (min, max, Beschriftungen) | Skala mit Definition | ganze Zahl | 1 |
| `Choice` (Optionen, mehrfach ja/nein) | Einfach-/Mehrfachauswahl | Menge von Options-IDs | 1 |
| `TriState` | Ja/Nein/Unbekannt | `YES`, `NO`, `UNKNOWN` | 1 |
| `Group` (Unterfelder) | zusammengesetzter Messwert (Blutdruck) | Werte der Unterfelder | 2 |
| `DateTime` | Datum/Uhrzeit | Zeitpunkt | 2 |
| `BodyRegion` | Körperregion | Menge von Regionen | 3 |
| `Attachment` | Foto/Datei-Referenz | Referenz | 3 |
| `EventRef` | Referenz auf verknüpftes Event | Event-ID | 3 |

Hinweise:
- **Dauer** ist keine Feldart, sondern `Event.durationMinutes` (für den Kalender). Die Oberfläche bietet Voreinstellungen wie 30 Minuten, 2 Stunden, 10 Stunden.
- **Wiederverwendung** in Version 1 über Vorlagen im Code (z. B. "Schmerzintensität 0–10"), die beim Anlegen einer Definition *kopiert* werden. Gemeinsam geteilte Felddefinitionen kommen später.
- JSON wird im Repository mit `kotlinx-serialization` (sealed interface) umgewandelt. In der Datenbank bleibt es einfacher Text.

## 6. Zeit und Kalender

- `occurredAt` ist der Ereigniszeitpunkt (Beginn). `recordedAt` ist der Erfassungszeitpunkt. Bei Umwandlung einer QuickNote ist `occurredAt` zunächst `enteredAt` und änderbar.
- Mit `durationMinutes` zeigt der Kalender das Event an **allen** berührten Tagen (Zeitzone des Geräts). Ohne Dauer nur am Starttag. Ein Event bleibt ein Datensatz.
- Der Kalendertag ist eine berechnete Zusammenfassung (offene QuickNotes, Events, Medikamente genommen). Es gibt keine Tag-Tabelle.

## 7. Umsetzung in Stufen

**Stufe 1 (Version 1):** `EventType`, Feldarten Text, Terms, Number, Scale, Choice, TriState, Tabellen aus Abschnitt 3, deutsches Start-Set, Repository für QuickNote → Event, neuer Kalender mit Schnelleingabe, Eingabe-Komponenten je Feldart.

**Bewusst später:**
- Feldarten `Group`, `DateTime`, `BodyRegion`, `Attachment`, `EventRef`.
- Gemeinsam geteilte Felddefinitionen mit Versionierung (Bedeutung alter Werte bei geänderter Skala).
- Indextabelle für Werte, falls Auswertungen über JSON zu langsam werden.
- CGM und große Mengen von Messwerten über Health Connect/HealthKit statt als einzelne Events.
- Einheiten und Umrechnung (mg/dl, mmol/l), Messkontext als eigene Auswahl.
- Gewichtetes Lernen und Verwaltung der gelernten Zuordnungen in den Einstellungen.
- Medikamenten-Statistik, Musteranalyse, Erinnerungen, Export, Synchronisierung.
- Löschmarkierungen für Geräteabgleich.

## 8. Offene Punkte

- Wie ein Trackable in der gezielten Erfassung seine Events anbietet: zunächst bereits benutzte Definitionen plus Katalogsuche. Eine feste Zuordnung Definition ↔ Trackable ist eine spätere Option.
- Verhalten beim Archivieren eines Trackables, dessen Events nur dort liegen (Vorschlag: Events bleiben abrufbar, das Trackable wird nur ausgeblendet).
- Herkunft des deutschen Start-Sets.
