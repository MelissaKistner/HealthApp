package com.example.healthapp.shared.repository

import com.example.healthapp.shared.data.HealthDatabase
import com.example.healthapp.shared.data.quicknote.QuickNoteEntity
import kotlinx.coroutines.flow.Flow
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import com.example.healthapp.shared.data.trackable.SystemTrackableIds
import com.example.healthapp.shared.data.trackable.SystemTrackableIds.OTHER
import com.example.healthapp.shared.data.trackable.TrackableEntity

/*
database greift auf die HealtDataBase zu und stellt das Dao bereit, clock für das gesamte Repo die Systenuhr zur Verfügung
 */
class QuickNoteRepository(private val database: HealthDatabase, private val clock: Clock = Clock.System) {
    private val quickNoteDao = database.quickNoteDao()
    private val trackableDao = database.trackableDao()
    /*
    Flow Liste der Quicknotes
     */
    val quickNotes: Flow<List<QuickNoteEntity>> = quickNoteDao.observeAll()

    /*
    Macht aus der Eingabe eine Quicknote; cleanedText entfernt voran- oder nachgestellte Leerzeichen (Fallback: Falls das Ergebnis leer ist, wird abgebrochen und keine Quicknote erstellt.
    Erstellt UUID und Timestamp vom Moment der Erstellung basierend auf der Systemuhr. Ruft das Dao auf und fügt eine neue Quicknote hinzu.
     */
    @OptIn(ExperimentalUuidApi::class)
    suspend fun addQuickNote(text: String) {
        val cleanedText = text.trim()
        if (cleanedText.isBlank()) {
            return
        }
        val id: String = Uuid.random().toString()
        val timeStamp: Instant = clock.now()
        quickNoteDao.insert(
            quickNote = QuickNoteEntity(id, cleanedText, timeStamp, timeStamp)
        )

    }
    /*
    Für den Abgleich mit bereits gespeicherten Quicknote-Vorlagen: Leerzeichen werden entfernt (vorne, hinten, mehrere in der Mitte werden durch einzelne ersetzt), alles wird mit Kleinbuchstaben gespeichert
    Dieser normalisierte String wird zurückgegeben
     */
    private fun normalizeTerm(text: String): String {
        val normalized = text.trim().lowercase().replace("\\s+".toRegex(), " ")
        return normalized
    }
/*
greift auf die festgelegte ID von "Sonstiges" zu und checkt, ob es das Trackable schon gibt. Wenn nicht, wird en angelegt.
TODO Wichtig: "Sonstiges" als Namen überdenken. Muss endgültig festgelegt werden, weil er in der Datenbank landet
 */
    private suspend fun ensureOtherTrackable() {
        val otherId = OTHER
        if (trackableDao.getById(otherId) == null) {
            trackableDao.insert(TrackableEntity(otherId, "Sonstiges", true, null, clock.now()))
        }

    }
    /*
    Nur bei aktiv abgeschlossener Zuordnung aufrufen.
    Es wird gescheckt, ob es ausgewählte Trackables für das Event gibt. Falls das nicht so ist, wird in Sonstiges verschoben, indem die Sonstiges-ID in die Liste kommt.
    Sollte Sonstiges in der Liste aufgewählt sein (also Liste ist nicht leer, aber enthält Sonstiges), wird ebenfalls gecheckt, ob es bereits Sonstiges gibt und ansonsten angelegt.
    Eine leere Liste bedeutet die bewusste Zuordnung zu "Sonstiges".
     */
    private suspend fun resolveTrackableIds(idList: List<String>): List<String> {
        if (idList.isEmpty()) {
            ensureOtherTrackable()
            return listOf(OTHER)
        }
        if (OTHER in idList) {
            ensureOtherTrackable()
        }
        return idList



    }
}