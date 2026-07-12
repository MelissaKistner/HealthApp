package com.example.healthapp.data.model.QuickNotes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.healthapp.data.enums.QuickNoteStatus
import java.time.LocalDate
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Entity
data class QuickNoteEntity @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: LocalDate,
    val text: String,
    val createdAt: Instant,
    val assignmentStatus: QuickNoteStatus =
        QuickNoteStatus.UNASSIGNED
)
