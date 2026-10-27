package com.example.healthapp.shared.data.event

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.time.Instant

@Entity(tableName = "events",
    foreignKeys = [
        ForeignKey(
            // Name der Tabelle, auf die verwiesen wird
            entity = EventDefinitionEntity::class,
            // Welche Spalte ist der Primärschlüssel in der Tabelle?
            parentColumns = ["id"],
            // Welche Spalte hier im Event?
            childColumns = ["definitionId"],
            // Die Definition ist nicht löschbar, solange Events auf sie verweisen.
            onDelete = ForeignKey.RESTRICT
        )
    ],
    // Index fungiert als Inhaltsverzeichnis für eine schnelle Suche (wichtig bei Fremdschlüssel-Spalten)
    // über occurredAt werden zum Beispiel Events eines Tages abgefragt.
    indices = [Index("definitionId"), Index("occurredAt")]
    )
data class EventEntity(
    // verweist auf UUID des einzelnen Events
    @PrimaryKey val id: String,
    // Verweist auf die EventDefinition
    val definitionId: String,
    //Wann passiert(wird zur Startzeit)?
    val occurredAt: Instant,
    //Wann in der App erfasst?
    val recordedAt: Instant,
    // Optionale Dauer, bei null keine Angabe
    val durationMinutes: Int? = null,
    // Werte als JSON-Text
    val valuesJson: String = "{}",
    // Wann zuletzt geändert? Wichtig für Abgleich zwischen Geräten später.
    val updatedAt: Instant
    )