package com.example.healthapp.ui.Calender

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthapp.data.model.QuickNotes.DayEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

@Composable
fun DayCard(
    modifier: Modifier = Modifier,
    date: LocalDate,
    day: DayEntity
) {
    val formatter = DateTimeFormatter.ofPattern(
        "dd. MMM",
        Locale.GERMAN
    )
    val weekdayFormatter =
        DateTimeFormatter.ofPattern("EEE", Locale.GERMAN)
    val monthFormatter =
        DateTimeFormatter.ofPattern("MMM", Locale.GERMAN)
    val weekday = date.format(weekdayFormatter)
    val weekdayName = date.dayOfWeek.getDisplayName(
        TextStyle.FULL,
        Locale.GERMAN
    )
    val month = date.format(monthFormatter)
    val label = date.format(formatter)
    Column() {
        Card(modifier.height(100.dp).width(100.dp)) {
            Column(Modifier.padding(8.dp).fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(weekdayName)
                Text(label.toString())
                Row(modifier.fillMaxWidth(1f).wrapContentSize()) {

                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DayCardPreview() {
    // Use Theme here
    DayCard(date = LocalDate.now())
}