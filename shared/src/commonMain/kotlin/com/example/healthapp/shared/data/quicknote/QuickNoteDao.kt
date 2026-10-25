package com.example.healthapp.shared.data.quicknote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Datenbankzugriff für QuickNotes.
 *
 * In geteiltem Code müssen alle Funktionen entweder `suspend` sein oder einen
 * `Flow` zurückgeben. Blockierende Funktionen gibt es nur auf Android.
 */
@Dao
interface QuickNoteDao {

    @Insert
    suspend fun insert(quickNote: QuickNoteEntity)

    // Flow: Die Liste meldet sich selbst neu, sobald sich die Tabelle ändert.
    // So aktualisiert sich später die Oberfläche automatisch.
    @Query("SELECT * FROM quick_notes ORDER BY enteredAtEpochMillis DESC")
    fun observeAll(): Flow<List<QuickNoteEntity>>

    @Query("DELETE FROM quick_notes WHERE id = :id")
    suspend fun deleteById(id: String)
}
