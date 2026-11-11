# HealthApp

Android-App (Kotlin, Jetpack Compose), perspektivisch Kotlin Multiplatform.
Produktvision und Entscheidungen: `docs/PROJECT_VISION.md` (maßgeblich bei Konflikten, insbesondere Abschnitt 17).

## Module
- `:app` – Android-App: Oberfläche (Compose), Navigation, `MainActivity`. Bleibt `com.android.application`.
- `:shared` – Kotlin Multiplatform: Room-Datenbank, Datenmodelle, später Repositories und Fachlogik.
  Ziele: Android, `iosArm64`, `iosSimulatorArm64`. Keine Oberfläche.
  - `commonMain` muss plattformneutral bleiben (Datum/Zeit: `kotlin.time.Instant` und `kotlinx-datetime`).
  - Plattformspezifisch ist nur der Datenbank-Builder (`androidMain`, `iosMain`).
- Alter Code in `:app` (`data/model`, `data/enums`, Kalender) ist noch nicht migriert und wird
  im Zug des neuen Datenmodells abgelöst.
- iOS-Build auf diesem Mac: `export DEVELOPER_DIR=/Applications/Xcode.app/Contents/Developer`,
  solange `xcode-select` auf die Command Line Tools zeigt.
- Room bleibt bei 2.8.x (stabil). Room 3 ist noch Alpha und wird später geprüft.

## Fachliche Leitplanken
- Kein Diagnosewerkzeug. Keine Kausalbehauptungen, medizinisch vorsichtige Sprache.
- "Trackable" nicht als "Krankheit" bezeichnen oder modellieren (UI-Begriff noch offen).
- Erfassen vor Einordnen: Die Schnellerfassung darf nie an fehlenden Angaben scheitern.
  Der Originaltext einer Eingabe geht nie verloren (er wird als `note` ins Event übernommen).
- Erinnerungen und Hinweise ohne Druck und ohne Schuldzuweisung.
- Barrierefreiheit ist Produktziel: große Touchflächen, eindeutige Beschriftung,
  `contentDescription` für jedes Symbol.

## Domänenmodell (Kurzfassung)
- QuickNote: Freitext (nur `trim()`, Originalformulierung bleibt) + Eingabezeitpunkt. Nur ein Zustand (offen).
  Darf mehrere Beschwerden enthalten. Bleibt, bis die Zuordnung ausdrücklich abgeschlossen ist, und wird
  erst nach erfolgreicher Umwandlung in ein oder mehrere Events gelöscht (eine Transaktion).
- EventDefinition: Katalogeintrag mit Name, Eventtyp (Enum, 9 Werte) und Felddefinitionen (JSON).
  Felder gehören zur Definition, nicht zum Trackable. TermAssignment lernt "Text → Definition".
- Event: Instanz einer Definition; Werte als JSON (Feld-ID → Wert); gehört immer zu mindestens
  einem Trackable (Fallback: "Sonstiges", Systemeintrag mit fester ID, nicht löschbar, wird erst bei
  Bedarf angelegt, auch zusammen mit anderen Trackables wählbar); Ereigniszeitpunkt getrennt
  vom Erfassungszeitpunkt (`recordedAt` = Eingabezeitpunkt der QuickNote); optionale geschätzte Dauer
  (im Kalender an allen berührten Tagen); `note` = vollständiger Originaltext der QuickNote.
- Trackable: gruppiert Events (Tagebuch), hat keine Felder. Archivieren statt löschen.
- Vollständiges Datenmodell und Umsetzungsstufen: `docs/DATA_MODEL.md`.
- Day: berechnete Zusammenfassung für den Kalender, keine eigene Tabelle
  (außer bewusst anders entschieden).
- Vorschlagskatalog = die EventDefinitions: deutsche Begriffe, vom Nutzer pflegbar und ausblendbar.

## Technische Regeln
- Lokal-first (Room). Keine Netzwerk-Bibliotheken ohne vorherige Absprache.
- Neuer Code für Daten und Fachlogik soll KMP-tauglich sein
  (kein `java.time`, keine Android-Klassen im Datenmodell).
- Keine Lambdas oder UI-Typen in serialisierbaren Modellen.
- Stabile IDs (UUID) und Änderungszeitstempel für späteren Sync.
- Texte nicht fest im Code, sondern vorbereitet für Mehrsprachigkeit.

## Arbeitsweise
- Vor jeder Implementierung: Vorschlag erklären, Nutzerin prüft und versteht, dann umsetzen.
- Nichts löschen, committen oder pushen ohne ausdrückliche Freigabe.
  Freigabe liegt vor: Der alte Kalender-Code darf gelöscht werden, sobald der neue steht.
- Dependency-, Gradle- und Architekturänderungen vorher besprechen.

## Vorgemerkt
- Medikamenten-Einnahmetage-Statistik pro Trackable (siehe Vision, Abschnitt 17).
- Offene Entscheidungen zu QuickNote und Vorschlägen (Sortierung, Verknüpfung QuickNote ↔ Definitionen,
  mehrere Events aus einer QuickNote, endgültiger Name von "Sonstiges"): `docs/DATA_MODEL.md`, Abschnitt 8.
