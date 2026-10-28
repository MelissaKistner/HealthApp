package com.example.healthapp.shared.data.event

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.healthapp.shared.data.trackable.TrackableEntity

@Entity(
    tableName = "event_trackables",
    // Zusammengesetzter Primärschlüssel: Kombination muss einmalig sein, sodass ein Event immer nur einmal einem Trackable zugeordnet werden kann
    primaryKeys = ["eventId", "trackableId"],
    foreignKeys = [
        ForeignKey(
            entity = EventEntity::class,
            parentColumns = ["id"],
            childColumns = ["eventId"],
            // Event nicht löschbar, solange es zugeordnet ist
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = TrackableEntity::class,
            parentColumns = ["id"],
            childColumns = ["trackableId"],
            //Trackable nicht löschbar, solange Events drin liegen
            onDelete = ForeignKey.RESTRICT
        )
    ],
    // Kein Index für eventId, da sie als erste Spalte des Primärschlüssels schon einen hat
    indices = [Index("trackableId")]
)
data class EventTrackableEntity(
    val eventId: String,
    val trackableId: String
)