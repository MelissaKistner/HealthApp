# HealthApp – Produktvision und fachliches Konzept

**Status:** Grundlegende Produktvision mit definierten Anforderungen und offenen Entwicklungsfragen  
**Projektphase:** Frühe Entwicklung – erste UI-Komponenten, Enums und Datenmodelle vorhanden  
**Plattform:** Derzeit Android (Kotlin / Jetpack Compose), perspektivisch Kotlin Multiplatform für Android und iOS

---

## 1. Ausgangssituation und Problemstellung

Menschen mit mehreren chronischen Erkrankungen, Allergien, wiederkehrenden Beschwerden oder anderen gesundheitlichen Themen stehen häufig vor dem Problem, zahlreiche unterschiedliche Informationen dokumentieren zu müssen.

Für diese Dokumentation existieren viele spezialisierte Anwendungen: beispielsweise Migränetagebücher, Zyklustracker, Allergietagebücher oder Apps zur Dokumentation von Medikamenteneinnahmen.

Wer mehrere gesundheitliche Themen gleichzeitig beobachten möchte, muss deshalb häufig verschiedene Apps parallel verwenden.

Das führt zu zwei zentralen Problemen.

### Fragmentierte Gesundheitsdaten

Die Informationen werden getrennt voneinander erfasst und ausgewertet.

Dadurch bleibt häufig unsichtbar, ob unterschiedliche Beschwerden oder gesundheitliche Ereignisse zeitlich miteinander zusammenhängen.

Beispielsweise:

- Treten Migräneattacken häufiger während allergischer Beschwerden auf?
- Kommen Magen-Darm-Probleme besonders häufig während der Menstruation vor?
- Überschneiden sich bestimmte Beschwerden wiederholt?
- Gibt es Auffälligkeiten, die innerhalb eines einzelnen Gesundheitstagebuchs nicht erkennbar wären?

Die Nutzerinnen und Nutzer müssten diese Zusammenhänge bislang selbst entdecken, indem sie mehrere Dokumentationen miteinander vergleichen.

### Hoher Dokumentationsaufwand

Viele Anwendungen verlangen bereits bei der Eingabe eines Ereignisses mehrere Schritte: eine Erkrankung auswählen, ein Formular öffnen, Symptome kategorisieren, zusätzliche Informationen eingeben und schließlich speichern.

Gerade Menschen mit chronischen Erkrankungen haben jedoch nicht immer die Energie, Zeit oder Konzentrationsfähigkeit, ihre Beschwerden sofort vollständig zu dokumentieren.

Häufig werden Beobachtungen deshalb auf später verschoben und anschließend vergessen.

HealthApp soll beide Probleme gemeinsam lösen.

## 2. Grundidee und Zielsetzung

HealthApp ist eine zentrale Anwendung zur möglichst einfachen Dokumentation verschiedener gesundheitlicher Ereignisse und zur Erkennung möglicher Zusammenhänge zwischen diesen Ereignissen.

Nutzerinnen und Nutzer können innerhalb einer einzigen App verschiedene gesundheitliche Themen verwalten und mehrere themenbezogene Tagebücher führen.

Gleichzeitig werden alle erfassten Ereignisse in einer gemeinsamen Datenbasis gespeichert und auf einer übergreifenden Zeitachse sichtbar gemacht.

Die App soll langfristig selbstständig nach wiederkehrenden zeitlichen Mustern und auffälligen gemeinsamen Häufigkeiten suchen.

Dadurch können Zusammenhänge sichtbar werden, die bei einer isolierten Betrachtung einzelner Erkrankungen oder Beschwerden möglicherweise unentdeckt bleiben.

**HealthApp ist ausdrücklich kein Diagnosewerkzeug.**

Sie soll weder Krankheiten diagnostizieren noch Ursachen behaupten oder medizinische Entscheidungen treffen.

Ihr Zweck ist es, die Selbstbeobachtung zu vereinfachen, Informationen sinnvoll zusammenzuführen und möglicherweise relevante Beobachtungen als Grundlage für ärztliche Gespräche aufzubereiten.

## 3. Grundlegendes Datenkonzept: Events und Trackables

Die Anwendung unterscheidet zwei grundlegende Konzepte.

### Events

Events sind einzelne dokumentierte gesundheitliche Ereignisse oder Beobachtungen.

Beispiele:

- Kopfschmerzen
- Schwindel
- Übelkeit
- Allergische Beschwerden
- Beginn der Menstruation
- Medikamenteneinnahme

Ein Event wird grundsätzlich unabhängig von einer bestimmten Erkrankung oder einem bestimmten Gesundheitsthema erfasst und gespeichert.

Es besitzt mindestens eine Bezeichnung sowie einen zeitlichen Bezug.

Weitere Informationen können optional ergänzt werden.

**Ein Event muss nicht einem Trackable zugeordnet sein, um existieren zu können.**

> **Hinweis (siehe Abschnitt 17):** Dieser noch nicht eingeordnete Zustand wird technisch als *QuickNote* umgesetzt. Sobald aus einer QuickNote ein Event wird, gehört es zu mindestens einem Trackable (Fallback: das Rest-Trackable „Sonstiges“). Die Kernaussage bleibt bestehen: Erfassen ist ohne Einordnung möglich.

### Trackables

Trackables sind übergeordnete gesundheitliche Themen, die Nutzerinnen und Nutzer beobachten und dokumentieren möchten.

Beispiele:

- Migräne
- Allergien
- Menstruation oder Zyklus
- Magen-Darm-Beschwerden
- Schlaf
- Andere chronische oder wiederkehrende gesundheitliche Themen

Der Begriff *Trackable* wurde bewusst gewählt, weil nicht jedes beobachtete Thema eine Krankheit ist.

Die App darf gesundheitliche Vorgänge wie Menstruation nicht durch ihr Datenmodell oder ihre Sprache automatisch als Erkrankung einordnen.

Der technische Begriff kann zunächst beibehalten werden. Eine passende, leicht verständliche deutsche Bezeichnung für die Benutzeroberfläche wird später festgelegt.

### Beziehung zwischen Events und Trackables

Events können einem oder mehreren Trackables zugeordnet werden.

Beispielsweise kann das Event „Kopfschmerzen“ dem Trackable „Migräne“ zugeordnet werden.

Ein Event kann zunächst aber auch vollständig ohne Zuordnung gespeichert werden.

Die Zuordnung kann später vorgenommen, ergänzt oder verändert werden.

Mehrere Zuordnungen sollen grundsätzlich möglich sein, ohne dass dafür identische Ereignisse mehrfach erfasst werden müssen.

Die konkrete technische Ausgestaltung der Beziehungen wird bei der Architekturplanung entschieden.

## 4. Zentrales UX-Prinzip: Erfassen vor Einordnen

Einer der wichtigsten Grundsätze der App ist, dass ein gesundheitliches Ereignis sofort dokumentiert werden können muss.

Die Dokumentation darf nicht daran scheitern, dass gerade keine Zeit oder Energie für eine vollständige Einordnung vorhanden ist.

**Das Erfassen und das Kategorisieren eines Ereignisses sind zwei voneinander unabhängige Arbeitsschritte.**

Nutzerinnen und Nutzer können beides unmittelbar hintereinander erledigen, müssen es aber nicht.

Ein unvollständig dokumentiertes Ereignis ist wertvoller als eine Beobachtung, die aufgrund zu hoher Eingabehürden überhaupt nicht gespeichert wird.

Daraus ergeben sich zwei gleichberechtigte Erfassungswege.

## 5. Erfassungsweg A: Schnellerfassung

Beim Öffnen der App soll die unmittelbare Eingabe eines Ereignisses im Mittelpunkt stehen.

Das wichtigste Element des Startbildschirms ist deshalb ein sofort verfügbares Freitextfeld.

Beispielsweise:

*Kopfschmerzen*

Nach dem Bestätigen wird das Event direkt gespeichert.

Dabei werden Datum und Uhrzeit automatisch erfasst.

Weder die Auswahl eines Trackables noch eine detaillierte Symptombeschreibung sind notwendig.

Die Nutzerin oder der Nutzer kann die App danach unmittelbar wieder schließen.

### Wiederverwendbare Events und Eingabevorschläge

Bereits verwendete Eventbezeichnungen werden gespeichert und für zukünftige Eingaben vorgeschlagen.

Während der Texteingabe soll die App in Echtzeit passende vorhandene Events anbieten.

Wer beispielsweise „Kop“ eingibt, könnte sofort den Vorschlag „Kopfschmerzen“ sehen.

Dieser Vorschlag lässt sich direkt auswählen, etwa über ein einfach antippbares Chip-Element.

Damit soll die Dokumentation häufig wiederkehrender Ereignisse zunehmend schneller werden.

Die Vorschläge dürfen die freie Eingabe neuer Ereignisse nicht einschränken.

### Spätere Vervollständigung

Schnell erfasste Events können zu einem späteren Zeitpunkt ergänzt werden.

Beispielsweise können anschließend:

- Ein oder mehrere Trackables zugeordnet werden.
- Intensität oder weitere Details ergänzt werden.
- Begleitende Informationen wie Medikamente erfasst werden.
- Der tatsächliche Ereigniszeitpunkt korrigiert werden, wenn die Erfassung nachträglich erfolgt.

Die konkrete Ausgestaltung der zusätzlichen Eingabefelder wird im weiteren Entwicklungsprozess festgelegt.

## 6. Erfassungsweg B: Gezielte Dokumentation innerhalb eines Trackables

Neben der Schnellerfassung können Nutzerinnen und Nutzer ein bestimmtes Trackable öffnen und dort gezielt ein Event dokumentieren.

Beispielsweise:

**Trackable: Migräne**

Innerhalb dieses Bereichs wird das Event „Kopfschmerzen“ angelegt.

Dabei können direkt zusätzliche Informationen eingegeben werden, beispielsweise:

- Stärke der Kopfschmerzen
- Weitere relevante Symptome
- Eingenommene Medikamente
- Gegebenenfalls weitere zum Trackable passende Angaben

Das Event ist in diesem Fall bereits bei seiner Erfassung dem entsprechenden Trackable zugeordnet.

Dieser Weg richtet sich an Situationen, in denen Nutzerinnen und Nutzer eine gezielte und detailliertere Dokumentation vornehmen möchten.

Beide Erfassungswege führen zur selben zentralen Ereignisdatenbasis.

## 7. Thematische Tagebücher

Jedes Trackable soll eine eigene Ansicht erhalten, in der die zugeordneten Ereignisse gesammelt dargestellt werden.

Dadurch entstehen innerhalb einer einzigen Anwendung mehrere themenbezogene Gesundheitstagebücher.

Beispielsweise kann die Nutzerin alle Kopfschmerzereignisse im Bereich „Migräne“ betrachten, ohne gleichzeitig sämtliche anderen gesundheitlichen Beobachtungen angezeigt zu bekommen.

Diese themenbezogene Betrachtung soll unter anderem die Vorbereitung auf Arzttermine erleichtern.

Gleichzeitig bleiben die Ereignisse Bestandteil der übergreifenden Dokumentation.

Die einzelnen Tagebücher sind also unterschiedliche Ansichten einer gemeinsamen Datenbasis und keine voneinander isolierten Datensammlungen.

## 8. Gemeinsamer Kalender und übergreifende Zeitachse

Alle erfassten Events werden zusätzlich in einem zentralen Kalender zusammengeführt.

Dort wird sichtbar, welche gesundheitlichen Ereignisse an einem bestimmten Tag beziehungsweise in einem bestimmten Zeitraum dokumentiert wurden.

Die Kalenderansicht soll nicht ausschließlich anzeigen, dass Beschwerden vorhanden waren, sondern unterschiedliche Ereignisarten und deren zeitliches Zusammentreffen erkennbar machen.

Beispielsweise kann an einem Samstag sichtbar werden, dass sowohl Kopfschmerzen als auch allergische Beschwerden dokumentiert wurden.

### Darstellung

Die Kalendertage müssen ausreichend groß und übersichtlich gestaltet sein, damit mehrere Informationen erkennbar bleiben.

Denkbar sind beispielsweise Symbole, Chips oder andere klar unterscheidbare visuelle Elemente.

Die genaue Gestaltung ist noch offen.

Besonders wichtig ist, dass die Darstellung nicht überladen wirkt und auch bei vielen dokumentierten Ereignissen verständlich bleibt.

### Noch nicht zugeordnete Events

Events, denen noch kein Trackable zugeordnet wurde, sollen im Kalender besonders gekennzeichnet werden.

Ein gut erkennbares Symbol oder ein anderer eindeutiger Hinweis soll zeigen, dass noch eine Zuordnung offen ist.

Dabei darf der Eindruck nicht entstehen, das Event sei fehlerhaft oder unvollständig gespeichert.

Die Zuordnung ist optional; die Markierung dient lediglich als Orientierung und Erinnerung.

> **Hinweis (siehe Abschnitt 17):** Im Kalender werden noch nicht eingeordnete Einträge als offene QuickNotes pro Tag angezeigt. Der Kalendertag ist eine berechnete Zusammenfassung mit kleinen, eindeutigen Symbolen (offene Zuordnung, Beschwerden dokumentiert, Medikamente genommen).

## 9. Optionale Erinnerungen

Die App soll perspektivisch ermöglichen, zu einem selbst gewählten Zeitpunkt eine Erinnerung an noch nicht eingeordnete Events zu erhalten.

Beispielsweise am Abend, wenn mehr Zeit und Ruhe für die Ergänzung der Dokumentation vorhanden sind.

Eine mögliche Benachrichtigung wäre:

*„Du hast noch Ereignisse, die du ergänzen oder zuordnen kannst.“*

Erinnerungen müssen freiwillig und konfigurierbar sein.

Sie sollen unterstützen, nicht unter Druck setzen.

Insbesondere darf die App nicht suggerieren, dass eine vollständige Zuordnung verpflichtend ist.

## 10. Automatische Erkennung möglicher Zusammenhänge

Die langfristig wichtigste Auswertungsfunktion ist die übergreifende Analyse dokumentierter Events.

Während herkömmliche Gesundheitstagebücher häufig einzelne Erkrankungen isoliert betrachten, soll HealthApp Ereignisse aus verschiedenen gesundheitlichen Bereichen gemeinsam auswerten können.

Die Anwendung soll untersuchen, ob bestimmte Events wiederholt zeitlich miteinander auftreten oder andere auffällige zeitliche Muster erkennbar sind.

Beispielsweise:

- Kopfschmerzen und allergische Beschwerden treten auffällig häufig gemeinsam auf.
- Magen-Darm-Beschwerden werden besonders häufig während bestimmter Zyklusphasen dokumentiert.
- Bestimmte Symptome treten wiederholt im gleichen Zeitraum auf.

Die Nutzerinnen und Nutzer sollen dafür nicht eigenständig verschiedene Tabellen, Kalender oder Tagebücher vergleichen müssen.

Die Anwendung übernimmt die Suche nach möglichen Auffälligkeiten.

### Proaktive Hinweise

Wenn die Datenlage es rechtfertigt, kann HealthApp selbstständig auf eine mögliche Auffälligkeit aufmerksam machen.

Beispielsweise:

*„In deinen Aufzeichnungen treten Kopfschmerzen häufig zusammen mit allergischen Beschwerden auf. Vielleicht möchtest du diese Beobachtung bei deinem nächsten Arzttermin ansprechen.“*

Die Sprache muss dabei sachlich, verständlich und medizinisch vorsichtig bleiben.

### Grenzen der Auswertung

Ein zeitlicher Zusammenhang ist kein Beweis für eine ursächliche Verbindung.

HealthApp darf deshalb weder Diagnosen stellen noch behaupten, dass ein bestimmtes Ereignis ein anderes verursacht.

Auch häufiges gemeinsames Auftreten ist nicht automatisch eine aussagekräftige Auffälligkeit.

Die spätere Auswertung muss unter anderem berücksichtigen:

- Wie häufig die einzelnen Ereignisse grundsätzlich auftreten.
- Welche Zeiträume betrachtet werden.
- Ob ausreichend Beobachtungen vorhanden sind.
- Ob die Daten für eine sinnvolle Aussage ausreichen.
- Wie Ergebnisse verständlich und ohne irreführende Schlussfolgerungen dargestellt werden können.

Die konkreten statistischen Verfahren und Mindestanforderungen an die Datenqualität werden erst in einer späteren Entwicklungsphase definiert.

## 11. Zeitliche Erfassung

Für die spätere Auswertung ist eine korrekte zeitliche Einordnung der Events wichtig.

Deshalb muss grundsätzlich zwischen dem Zeitpunkt der Dokumentation und dem tatsächlichen Zeitpunkt eines Ereignisses unterschieden werden können.

Wird beispielsweise abends ein Ereignis nachgetragen, das bereits morgens stattgefunden hat, sollte die App diese Information korrekt erfassen können.

Bei einer Schnellerfassung kann zunächst die aktuelle Uhrzeit als Ereigniszeitpunkt verwendet werden.

Eine spätere Korrektur muss möglich sein.

Ob und wie zusätzlich längere Ereigniszeiträume mit Beginn und Ende erfasst werden, ist noch festzulegen.

## 12. UX- und Accessibility-Grundsätze

HealthApp richtet sich insbesondere an Menschen, für die gesundheitliche Dokumentation durch körperliche oder kognitive Belastungen erschwert sein kann.

Die Anwendung muss deshalb möglichst einfach, verständlich und alltagstauglich gestaltet werden.

Wichtige Grundsätze sind:

- Möglichst wenige Schritte bei häufigen Aufgaben.
- Sofortiger Zugang zur Schnellerfassung.
- Keine Verpflichtung, alle Informationen unmittelbar einzugeben.
- Klare, verständliche Sprache.
- Gut erkennbare und eindeutig beschriftete Bedienelemente.
- Übersichtliche und ausreichend große Interaktionsflächen.
- Gute Lesbarkeit und zugängliche Gestaltung.
- Niedrige kognitive Belastung.
- Sinnvolle Voreinstellungen und Eingabevorschläge.
- Keine unnötigen Unterbrechungen des Dokumentationsablaufs.
- Erinnerungen und Hinweise ohne Druck oder Schuldzuweisungen.

Die App soll sich an die verfügbaren Ressourcen der Nutzerinnen und Nutzer anpassen, nicht umgekehrt.

**Die einfachste mögliche Interaktion ist nicht nur eine Komfortfunktion, sondern ein grundlegendes Produktziel.**

## 13. Technische Ausgangslage und Plattformstrategie

Das Projekt wurde zunächst als native Android-Anwendung mit Kotlin und Jetpack Compose begonnen.

Bisher existieren hauptsächlich erste UI-Bestandteile, Enums und Datenmodelle.

Die technische Architektur ist noch nicht vollständig ausgearbeitet und kann deshalb vor der weiteren Entwicklung überprüft und angepasst werden.

Perspektivisch soll die App sowohl unter Android als auch unter iOS verfügbar sein.

Dafür wird Kotlin Multiplatform als mögliche technische Grundlage angestrebt.

Dabei sollen sinnvoll gemeinsam nutzbare Bestandteile geteilt werden, ohne die Bedienkonventionen der jeweiligen Plattform zu vernachlässigen.

Ob Compose Multiplatform für die gesamte Oberfläche eingesetzt wird oder ob einzelne Bereiche plattformspezifisch umgesetzt werden, ist noch offen.

Die Entscheidung soll anhand der Anforderungen an Wartbarkeit, Verständlichkeit, UX und Accessibility getroffen werden.

Aktuelle stabile Android- und Apple-Technologien und offizielle Designempfehlungen sollen berücksichtigt werden.

## 14. Datenschutz und Verantwortung

Da HealthApp besonders sensible Gesundheitsdaten verarbeitet, müssen Datenschutz und Datensicherheit bereits bei der Architekturplanung berücksichtigt werden.

Dabei sind insbesondere zu klären:

- Speicherung und Schutz der Gesundheitsdaten.
- Lokale Datenhaltung und mögliche spätere Synchronisierung.
- Möglichkeiten zur Datensicherung und Wiederherstellung.
- Datenexport, insbesondere zur Verwendung bei Arztterminen.
- Löschung und Kontrolle über die eigenen Daten.
- Datenschutzrechtliche und gegebenenfalls medizinprodukterechtliche Anforderungen.

Es ist bislang keine endgültige technische Lösung für diese Fragen festgelegt.

Die Anwendung soll grundsätzlich so entwickelt werden, dass Nutzerinnen und Nutzer nachvollziehen und kontrollieren können, was mit ihren Daten geschieht.

## 15. Entwicklungsstand und offene Entscheidungen

Die bisherige Implementierung bildet noch nicht sämtliche beschriebenen Funktionen ab.

Vor der weiteren Entwicklung sollen die vorhandenen Modelle, Enums und UI-Komponenten daraufhin untersucht werden, ob sie die Produktvision sinnvoll unterstützen.

Insbesondere sind noch zu klären:

- Die konkrete Datenstruktur für Events und Trackables.
- Die technische Umsetzung flexibler Zuordnungen.
- Unterschiedliche Eventtypen und zusätzliche Eingabefelder.
- Die Integration von Medikamenten und weiteren Beobachtungsdaten.
- Die Struktur der Schnellerfassung und der Vorschlagsfunktion.
- Die Darstellung und Navigation im Kalender.
- Die Gestaltung thematischer Tagebücher.
- Die technische Umsetzung der späteren Musteranalyse.
- Datenschutz, Export und mögliche Synchronisierung.
- Die Migrationsstrategie zu Kotlin Multiplatform.

Bestehende Implementierungen sollen nicht allein deshalb beibehalten werden, weil sie bereits geschrieben wurden.

Gleichzeitig sollen funktionierende Bestandteile nicht ohne nachvollziehbaren Grund durch kompliziertere Lösungen ersetzt werden.

Architekturentscheidungen müssen sich an der Produktvision, den tatsächlichen Anforderungen und der langfristigen Wartbarkeit orientieren.

## 16. Übergeordneter Leitgedanke

**HealthApp soll Menschen mit komplexen gesundheitlichen Lebensrealitäten Dokumentationsarbeit abnehmen, statt neue Arbeit zu erzeugen.**

Die App soll gesundheitliche Beobachtungen möglichst unmittelbar erfassbar machen, Informationen aus unterschiedlichen Lebens- und Gesundheitsbereichen zusammenführen und zeitliche Zusammenhänge sichtbar machen, die sonst möglicherweise verborgen bleiben.

Nicht die größtmögliche Menge dokumentierter Daten ist das Ziel, sondern deren tatsächlicher Nutzen.

Menschen sollen ihre Gesundheit nicht perfekt dokumentieren müssen, um von ihren Aufzeichnungen profitieren zu können.

Die Anwendung soll dabei helfen, ihre eigenen Beobachtungen besser zu verstehen und informierter mit medizinischen Fachpersonen darüber sprechen zu können.

## 17. Getroffene Entscheidungen (Stand 24.10.2026)

Dieser Abschnitt hält Entscheidungen fest, die nach dem ursprünglichen Vision-Text getroffen wurden. Bei Abweichungen gilt dieser Abschnitt.

### Ablauf QuickNote → Event

- Eine QuickNote besteht aus Text und Eingabezeitpunkt. Sie ist noch kein Event.
- Bei der Zuordnung wird daraus ein Event: Die Nutzerin wählt Trackable und Eventart (z. B. Symptom, Medikament). Die QuickNote wird danach gelöscht.
- Jede QuickNote kann einem Trackable zugeordnet werden. Wer sie bewusst keiner Krankheit oder keinem Thema zuordnen will, ordnet sie dem Rest-Trackable zu.
- Das Rest-Trackable heißt „Sonstiges“. Es ist ein Systemeintrag: immer vorhanden, nicht löschbar, neutral benannt (kein Bezug zu Krankheit). Dort landen z. B. Schmerzmittel bei Rückenschmerz nach dem Verheben.
- Ein Event gehört immer zu mindestens einem Trackable. Nur die QuickNote ist der Zustand „noch nicht eingeordnet“.
- Der Eingabezeitpunkt ist der Standard-Ereigniszeitpunkt und kann geändert werden.
- Statt Start- und Endzeit gibt es eine geschätzte Dauer (z. B. 2, 5 oder 10 Stunden).

### Events und Tage

- Beeinträchtigung, Intensität usw. gehören zum Event, nicht zum Tag. Jede Erkrankung muss einzeln vorgelegt werden können.
- Der Tag ist eine berechnete Zusammenfassung für den Kalender, ohne eigene Datenbanktabelle: offene QuickNotes, Events vorhanden, Medikamente genommen. Dargestellt als kleine, eindeutige Symbole.
- Eine Medikamenteneinnahme ist ein eigenes Event mit eigenem Zeitpunkt und eigener Wirkungsangabe. Sie zählt immer in die Medikamenten-Einnahmetage, auch im Trackable „Sonstiges“.
- Weitere Angaben, die in kein Feld passen, kommen in das Freitextfeld des Trackables.

### Vorschlagskatalog

- Großes, deutsches Start-Set (gängige Medikamente, typische Symptome).
- Die Nutzerin kann Begriffe hinzufügen und löschen (später in den Einstellungen).
- Neue Begriffe aus der Eingabe werden automatisch im Katalog gespeichert.
- Die Herkunft des Start-Sets ist noch offen.

### Trackables

- Die Nutzerin legt eigene Trackables mit eigenen Feldern an.
- Vorgegebene Vorlagen sind erlaubt, aber immer anpassbar.
- Die Eventart bestimmt zusätzlich passende Felder (z. B. Wirkung bei Medikamenten).

### Technik

- Ausschließlich lokale Speicherung (Room) in der ersten Version.
- Eingabewerte und Felddefinitionen werden als JSON serialisiert.
- Retrofit, Moshi und Logging-Interceptor werden entfernt.
- Stabile IDs und Änderungszeitstempel als Vorsorge für späteren Geräteabgleich (z. B. Firebase).
- Erst Migration zu Kotlin Multiplatform (nur Daten und Fachlogik geteilt), dann Weiterentwicklung. Die Oberfläche bleibt zunächst Android-Compose.

### Erste Version (Umfang)

- Eigene Trackables mit Feldern.
- Kalender als Startseite mit Schnelleingabe als Sheet, das standardmäßig geöffnet ist.
- Zuordnung von QuickNotes zu Events.
- Der bisherige Kalender war ein Lernexperiment und wird neu gebaut.

### Vorgemerkt für später

- Statistik der Medikamenten-Einnahmetage pro Trackable (z. B. Medikament darf nur an x von 30 Tagen genommen werden).
- Auswertung der Wirkung pro Medikament.
- Einstellungen zur Pflege des Vorschlagskatalogs.
- Erinnerungen, Export, Musteranalyse.
