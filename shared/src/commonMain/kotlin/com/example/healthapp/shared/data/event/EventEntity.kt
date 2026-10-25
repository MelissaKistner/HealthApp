package com.example.healthapp.shared.data.event

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

@Entity(tableName = "event_definitions")
data class EventDefinitionEntity(
    @PrimaryKey val id: String,
    val name: String,
    val eventType: EventType,
    val fieldsJson: String,
    val isPredefined: Boolean,
    val archivedAt: Instant? = null,
    val updatedAt: Instant
)
