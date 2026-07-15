package com.example.healthapp.ui.calendar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthapp.data.enums.Effect
import com.example.healthapp.data.enums.Event
import com.example.healthapp.data.enums.Impact
import com.example.healthapp.data.model.QuickNotes.DayEntity
import com.example.healthapp.data.model.Trackables.Drugs
import java.time.LocalDate

@Composable
fun DayCardDetails(
    modifier: Modifier = Modifier,
    day: DayEntity,
    onNavigateBack: () -> Unit
) {
    Text(day.quickNotes.toString())
    Text(day.drugs.toString())
}

@Preview(showBackground = true)
@Composable
private fun DayCardDetailsPreview() {
    // Use Theme here
    DayCardDetails(day = DayEntity(
        date = LocalDate.parse("2026-07-10"),
        quickNotes = listOf(),
        events = listOf(Event.UNCATEGORIZED, Event.MEDS),
        drugs = Drugs(listOf("Sumatriptan", "Ibuprofen"), Effect.VERY_STRONG) ,
        symptoms = listOf("Ausschlag"),
        triggers = listOf("Hitze", "Stress"),
        impactJob = Impact.MEDIUM,
        privateImpact= Impact.LIGHT
    )) {}
}