package com.example.healthapp.shared.data.event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert
    suspend fun insert(event: EventDefinitionEntity)

    @Query("SELECT * FROM event_definitions WHERE archivedAt IS NULL ORDER BY name")
    fun observeAll(): Flow<List<EventDefinitionEntity>>

    @Query("DELETE FROM event_definitions WHERE id = :id")
    suspend fun deleteById(id: String)
}
