package com.example.healthapp.data.model.QuickNotes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.healthapp.data.enums.Event
import com.example.healthapp.data.enums.Impact
import com.example.healthapp.data.model.Trackables.Drugs
import java.time.LocalDate
import kotlin.time.ExperimentalTime

/**
 * TODO Entity bauen wie sonst auch?
 *
 * @property id
 * @property date
 * @property quickNotes
 * @property events
 * @property drugs
 * @property symptoms
 * @property triggers
 * @property informations
 * @property impact
 */
@Entity
data class DayEntity @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: LocalDate,
    val quickNotes: List<QuickNoteEntity> = emptyList(),
    val events: List<Event> = emptyList(),
    val drugs: Drugs,
    val symptoms: List<String> = emptyList(),
    val triggers: List<String> = emptyList(),
    val informations: String = "",
    val impactJob: Impact = Impact.NONE, //Tupel aus Art und Stärke?
    val privateImpact: Impact = Impact.NONE


)
