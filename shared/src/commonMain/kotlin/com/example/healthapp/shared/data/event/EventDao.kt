package com.example.healthapp.shared.data.event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Zugriff auf die Tabelle `event_definitions` (den Katalog der Eventdefinitionen).
 */
@Dao
interface EventDao {

    /** Legt eine Definition an. Gibt es die ID schon, bricht Room mit einem Fehler ab. */
    @Insert
    suspend fun insert(event: EventDefinitionEntity)

    /**
     * Liefert alle nicht archivierten Definitionen, alphabetisch nach Name.
     * Als Flow meldet sich die Funktion bei jeder Änderung der Tabelle erneut.
     */
    @Query("SELECT * FROM event_definitions WHERE archivedAt IS NULL ORDER BY name")
    fun observeAll(): Flow<List<EventDefinitionEntity>>

    /**
     * Löscht eine Definition endgültig. Vorsicht: Laut Konzept werden Definitionen eigentlich
     * archiviert statt gelöscht. Diese Funktion ist vorerst nur für Aufräumfälle gedacht.
     */
    @Query("DELETE FROM event_definitions WHERE id = :id")
    suspend fun deleteById(id: String)
}
