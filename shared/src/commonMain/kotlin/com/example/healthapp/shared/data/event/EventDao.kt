package com.example.healthapp.shared.data.event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    //Insert als Standard - ID bereits vergeben -> Room bricht ab
    @Insert
    suspend fun insert(event: EventEntity)

    // einzelnes Event wird abgefragt. Gibt null zurück, wenn ID nicht vorhanden
    @Query("SELECT * FROM events WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): EventEntity?

    // Flow aller Events, jüngste zuerst, reagiert auf Änderungen.
    @Query("SELECT * FROM events ORDER BY occurredAt DESC")
    fun observeAll(): Flow<List<EventEntity>>
}
