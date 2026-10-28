package com.example.healthapp.shared.data.event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventTrackableDao {
    // legt eine Verknüpfung an - wenn es sie schon gibt, passiert nichts
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(link: EventTrackableEntity)

    // Zeigt die IDs aller Trackables, zu denen Events gehören
    @Query("SELECT trackableId FROM event_trackables WHERE eventId = :eventId")
    suspend fun getTrackableIdsForEvent(eventId: String): List<String>

    /* Gibt alle Events eines Trackables von neu nach alt - Dokumentation
    Dabei verbindet JOIN die Tabellen und sucht zu jeder zeile aus event_trackable das passende Event
     */
    @Query(
        "SELECT events.* FROM events INNER JOIN event_trackables ON events.id = event_trackables.eventId WHERE event_trackables.trackableId = :trackableId ORDER BY events.occurredAt DESC")
    fun observeEventsForTrackable(trackableId: String): Flow<List<EventEntity>>


}
