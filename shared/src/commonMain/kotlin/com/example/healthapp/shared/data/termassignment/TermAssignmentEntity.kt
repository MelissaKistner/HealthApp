package com.example.healthapp.shared.data.termassignment

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.healthapp.shared.data.event.EventDefinitionEntity
import kotlin.time.Instant

@Entity(
    tableName = "term_assignment",
    // Ein Begriff kann zu mehreren Definitionen gehören und andersrum - wichtig ist, dass die Kombi nur einmal vorhanden ist.
    primaryKeys = ["term", "definitionId"],
    foreignKeys = [
        ForeignKey(
            entity = EventDefinitionEntity::class,
            parentColumns = ["id"],
            childColumns = ["definitionId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    // term braucht keine Indizes (erste Spalte des Primärschlüssels), definitionId als zweite braucht Indices
    indices = [Index("definitionId")]
    )

data class TermAssignmentEntity (
    // normalisierter Text
    val term: String,
    // Zugehörigkeit zu Text
    val definitionId: String,
    // Anzahl der Nutzungen
    val useCount: Int,
    //letzte Nutzung hilft, sinnvolle Vorschläge zu geben
    val lastUsedAt: Instant
)