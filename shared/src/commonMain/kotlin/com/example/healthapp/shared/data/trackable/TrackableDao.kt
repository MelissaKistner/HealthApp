package com.example.healthapp.shared.data.trackable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Zugriff auf die Tabelle `trackables`. Das DAO enthält nur Datenbankabfragen.
 * Regeln wie "Sonstiges darf nicht archiviert werden" prüft später das Repository.
 */
@Dao
interface TrackableDao {

    /**
     * Legt ein Trackable an. Gibt es die ID schon, passiert nichts (kein Fehler, kein Überschreiben).
     * Dadurch kann ein System-Trackable mit fester ID gefahrlos mehrfach angelegt werden.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trackable: TrackableEntity)

    /**
     * Liefert alle nicht archivierten Trackables, alphabetisch nach Name.
     * Als Flow meldet sich die Funktion bei jeder Änderung der Tabelle erneut,
     * sodass die Oberfläche die Liste automatisch aktualisieren kann.
     */
    @Query("SELECT * FROM trackables WHERE archivedAt IS NULL ORDER BY name")
    fun observeAll(): Flow<List<TrackableEntity>>

    /**
     * Sucht ein einzelnes Trackable über seine ID, auch wenn es archiviert ist.
     * Gibt `null` zurück, wenn es keines mit dieser ID gibt, z. B. wenn "Sonstiges"
     * noch nie gebraucht und deshalb noch nicht angelegt wurde.
     */
    @Query("SELECT * FROM trackables WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): TrackableEntity?
}
