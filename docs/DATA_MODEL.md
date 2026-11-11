# HealthApp – Datenmodell (Entwurf für Version 1)

Stand: 11.11.2026 (Datenbank-Version 6). Ergänzt `docs/PROJECT_VISION.md` (Abschnitt 17). Bei Widersprüchen gilt dieses Dokument für das Datenmodell.

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
| `QuickNote` (vorhanden) | `id`, `text`, `enteredAt`, `updatedAt` | Freie, schnelle Texteingabe, auch mit mehreren Beschwerden. Der Text wird nur mit `trim()` gekürzt. Wird erst nach bestätigter, erfolgreicher Umwandlung gelöscht. |
| `EventDefinition` | `id`, `name`, `eventType`, `fieldsJson`, `isPredefined`, `archivedAt?`, `updatedAt` | Der Katalog. Liefert Typ und Felder. |
| `TermAssignment` | `term` (normalisiert), `definitionId`, `useCount`, `lastUsedAt` | Primärschlüssel (`term`, `definitionId`). Lernt "Text → Definition". |
| `Event` | `id`, `definitionId`, `occurredAt`, `recordedAt`, `durationMinutes?`, `note?`, `valuesJson`, `updatedAt` | Ein erfasstes Ereignis. Dauer gilt für alle Typen. `note` enthält den vollständigen, unveränderten Originaltext der QuickNote und ist danach frei bearbeitbar. |
| `Trackable` | `id`, `name`, `isSystem`, `archivedAt?`, `updatedAt` | "Sonstiges" hat `isSystem = true`, ist nicht archivierbar und nicht löschbar. |
| `EventTrackable` | `eventId`, `trackableId` | Verknüpfung (n:m). Primärschlüssel (`eventId`, `trackableId`). |

Regeln, die der Code prüft (nicht die Datenbank):
- Ein Event gehört immer zu mindestens einem Trackable (Fallback "Sonstiges").
- Die Umwandlung QuickNote → Event läuft in einer Transaktion: Definition bestimmen, Event anlegen, Trackables verknüpfen, Zuordnung lernen, QuickNote löschen.
- Eine QuickNote bleibt erhalten, bis die Nutzerin die Zuordnung ausdrücklich abschließt. Sie wird erst gelöscht, wenn die Umwandlung erfolgreich abgeschlossen ist. Bei Fehler oder Abbruch bleibt sie erhalten.
- Aus einer QuickNote können mehrere Events entstehen (z. B. "Komischer Schwindel, Ausschlag und Herzrasen"). Jedes Event bekommt eine eigene ID und eine eigene Definition. `note` enthält bei jedem dieser Events den vollständigen Originaltext (bewusst kopiert, siehe Abschnitt 8).
- `recordedAt` übernimmt `QuickNote.enteredAt`. Den Zeitpunkt der Umwandlung trägt `updatedAt`.
- **"Sonstiges"** ist ein eigenständiges System-Trackable mit fester, nie zu ändernder ID (`SystemTrackableIds.OTHER`, `00000000-0000-0000-0000-000000000001`). Es wird erst angelegt, wenn es gebraucht wird (per Suche nach der ID, nicht beim Anlegen der Datenbank). Eine leere Trackable-Liste bedeutet nur im bewusst abgeschlossenen Zuordnungsprozess "Sonstiges". Es darf auch zusammen mit anderen Trackables gewählt werden (z. B. "Migräne" und "Sonstiges"). Die Anlage muss sowohl bei leerer Liste als auch bei enthaltener `OTHER`-ID sichergestellt werden. Der Anzeigename "Sonstiges" ist ein Arbeitstitel und noch nicht endgültig.

## 4. Zuordnung von Begriffen (QuickNote → Definition)

**Originaltext und Suchtext sind getrennt.** Gespeichert wird der Text nur mit `trim()` (führende und abschließende Leerzeichen entfernt). Groß-/Kleinschreibung und Formulierung bleiben erhalten, leere Eingaben werden nicht gespeichert. Nur für die Suche wird der Text normalisiert (Kleinschreibung, Leerzeichen bereinigt). Die Normalisierung verändert den gespeicherten Text nie.

Der normalisierte Text wird in `TermAssignment` gesucht:

- **Eindeutig** (genau eine Definition): wird als Vorschlag angeboten, inklusive Typ und Feldern. Er wird nicht ungefragt übernommen.
- **Mehrdeutig** (mehrere): Auswahl anbieten, nichts erzwingen. Nie automatisch zuordnen.
- **Unbekannt** (keine): nicht klassifizieren. Nach Bestätigung durch die Nutzerin kann eine Definition vom Typ `GENERAL` ohne Felder entstehen, die später umklassifiziert werden kann.
- **Lernen:** Wählt die Nutzerin eine Definition, wird die Zuordnung gespeichert oder `useCount` erhöht. Änderungen der Zuordnung sind jederzeit möglich.

### Mehrere Begriffe in einer QuickNote (Ziel, Umsetzung offen)

Eine QuickNote darf mehrere Beschwerden oder Beobachtungen enthalten. Bekannte Begriffe sollen innerhalb des Textes erkannt und unabhängig voneinander angeboten werden. Mögliche Trefferarten: exakte Übereinstimmung, Präfix, vollständiges Wort innerhalb einer längeren Eingabe, später gegebenenfalls Synonyme oder persönlich erlernte Bezeichnungen.

**Stand der Umsetzung:** Bisher gibt es nur die Suche über den ganzen normalisierten Text (`TermAssignmentDao.findByTerm`, exakte Übereinstimmung) und das Lernen des ganzen Textes. Das reicht für das Erkennen einzelner Begriffe im Text nicht aus und muss noch überarbeitet werden.

### Live-Vorschläge (gewünschtes Verhalten, noch nicht umgesetzt)

- Bekannte Eventbegriffe werden während der Eingabe in Echtzeit vorgeschlagen. Auch bei leerem Eingabefeld können häufig verwendete Begriffe erscheinen.
- Vorschläge sind optional und dürfen die freie Eingabe nicht behindern.
- Neben einem erkannten Begriff gibt es eine Kapsel mit Plus, mit der er freiwillig übernommen bzw. verknüpft wird.
- Die Auswahl eines Vorschlags überschreibt den Freitext nie ungefragt.
- Mehrere erkannte Begriffe werden unabhängig voneinander angeboten. Dieselbe Definition darf nicht doppelt hinzugefügt werden.
- **Sortierung: offen.** Bisher ist vorgesehen, nach individueller Nutzungshäufigkeit (`useCount`) zu sortieren. Zusätzlich soll später die Trefferqualität berücksichtigt werden. Wie beides gewichtet wird, ist eine offene UX- und Implementierungsentscheidung. Ohne Eingabe sollen weiterhin häufig verwendete Begriffe zuerst erscheinen.

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

- `occurredAt` ist der Ereigniszeitpunkt (Beginn). `recordedAt` ist der Erfassungszeitpunkt. Bei Umwandlung einer QuickNote übernimmt `recordedAt` den Eingabezeitpunkt der QuickNote (`enteredAt`). `occurredAt` wird im Bearbeitungsprozess zunächst mit demselben Wert vorbelegt und kann korrigiert werden.
- Mit `durationMinutes` zeigt der Kalender das Event an **allen** berührten Tagen (Zeitzone des Geräts). Ohne Dauer nur am Starttag. Ein Event bleibt ein Datensatz.
- Der Kalendertag ist eine berechnete Zusammenfassung (offene QuickNotes, Events, Medikamente genommen). Es gibt keine Tag-Tabelle.

## 7. Umsetzung in Stufen

**Stufe 1 (Version 1):** `EventType`, Feldarten Text, Terms, Number, Scale, Choice, TriState, Tabellen aus Abschnitt 3, deutsches Start-Set, Repository für QuickNote → Event, neuer Kalender mit Schnelleingabe, Eingabe-Komponenten je Feldart.

**Stand der Umsetzung (11.11.2026):**
- Vorhanden: alle Tabellen aus Abschnitt 3 mit Entity und DAO (Datenbank-Version 6, inklusive `Event.note`), `EventType` mit Converter, `SystemTrackableIds.OTHER`.
- `QuickNoteRepository`: umgesetzt sind `addQuickNote` (nur `trim()`, leere Eingabe wird verworfen), `normalizeTerm`, `ensureOtherTrackable` und `resolveTrackableIds`.
- Noch offen: die Umwandlung QuickNote → Event(s) in einer Room-Transaktion, die Suche von Definitionen (Abschnitt 8), die Erkennung mehrerer Begriffe, die JSON-Felder, das Start-Set und die Oberfläche.

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
- **Sortierung der Vorschläge:** Wie werden Trefferqualität (exakt, Präfix, Wort im Text) und Nutzungshäufigkeit gewichtet? Ohne Eingabe gilt weiterhin die Häufigkeit. Noch kein Algorithmus beschlossen.
- **Verknüpfung QuickNote ↔ ausgewählte Definitionen:** Das Modell kennt sie nicht (`QuickNote` hat nur `id`, `text`, `enteredAt`, `updatedAt`). Varianten: nur im Speicher der Oberfläche, eigene Kreuztabelle `quick_note_definitions` (Primärschlüssel verhindert Doppelte, beim Löschen der QuickNote `CASCADE`) oder JSON-Spalte. Offen ist auch, ob die Auswahl schon beim Erfassen oder erst in der Bearbeitung dauerhaft gespeichert wird. Neigung: zunächst nur im Speicher.
- **Eine QuickNote, mehrere Events:** Wie `convertToEvent` mehrere Events aus einer QuickNote erzeugt (z. B. Liste von Paaren aus Definition und Trackables), ist offen.
- **Folge von `Event.note`:** Bei mehreren Events aus einer QuickNote steht der vollständige Text mehrfach in der Datenbank, und die gemeinsame Herkunft ist nur indirekt über gleiche `recordedAt` erkennbar. Bewusst akzeptiert (Datenmenge gering, Originaltext soll vollständig bleiben). Eine Verknüpfung als gemeinsame Herkunft wäre später nachrüstbar.
- **`EventDefinitionDao`:** braucht eine Suche nach ID und nach Name. Ob `deleteById` dort bleibt, ist offen (Leitlinie: ausblenden statt löschen).
- **Endgültiger Anzeigename** des Rest-Trackables ("Sonstiges" ist ein Arbeitstitel). Er wird in der Datenbank gespeichert und sollte vor einer Veröffentlichung feststehen.
