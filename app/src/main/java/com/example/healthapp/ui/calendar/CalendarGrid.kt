package com.example.healthapp.ui.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthapp.data.enums.Event
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale
import kotlin.collections.orEmpty

private const val TODAY_INDEX = Int.MAX_VALUE / 2

@Composable
fun CalendarGrid(
    eventsByDate: Map<LocalDate, List<Event>> = emptyMap(),
    onDateClick: (LocalDate) -> Unit = {},
    onNavigateToDetails: (String) -> Unit
) {

    val today = remember { LocalDate.now() }

    val gridState = rememberLazyGridState(
        initialFirstVisibleItemIndex = TODAY_INDEX - 4
    )
    //val date = today.plusDays((index - TODAY_INDEX).toLong())

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        state = gridState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = Int.MAX_VALUE,
            key = { index ->
                today
                    .plusDays((index - TODAY_INDEX).toLong())
                    .toEpochDay()
            }
        ) { index ->

            val date = today.plusDays(
                (index - TODAY_INDEX).toLong()
            )

            CalendarDayCard(
                date = date,
                events = eventsByDate[date].orEmpty(),
                isToday = date == today,
                onClick = {
                    onDateClick(date)
                }
            )
        }
    }
}

@Composable
fun CalendarDayCard(
    date: LocalDate,
    events: List<Event>,
    isToday: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isFirstDayOfMonth = date.dayOfMonth == 1

    val weekNumber = date.get(
        WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear()
    )

    val isEvenWeek = weekNumber % 2 == 0

    val containerColor = when {
        isToday ->
            MaterialTheme.colorScheme.primaryContainer

        isFirstDayOfMonth ->
            MaterialTheme.colorScheme.secondaryContainer

        isEvenWeek ->
            MaterialTheme.colorScheme.surfaceVariant

        else ->
            MaterialTheme.colorScheme.primary
    }

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.8f),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            if (isFirstDayOfMonth) {
                Text(
                    text = "${
                        date.month.getDisplayName(
                            TextStyle.FULL,
                            Locale.GERMAN
                        )
                    } ${date.year}",
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = date.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.GERMAN
                )
            )

            FlowRow {
                events.forEach { event ->
                    EventIcon(event)
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun CalendarGridPreview() {
//    // Use Theme here
//    CalendarGrid()
//}