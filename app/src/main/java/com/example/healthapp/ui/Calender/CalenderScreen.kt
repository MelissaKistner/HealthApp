package com.example.healthapp.ui.Calender

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.YearMonth


@Composable
fun ScrollableCalendar() {
    // Generiert z.B. die nächsten 12 Monate ab jetzt (2026)
    val currentMonth = YearMonth.now()
    val months = (0..11).map { currentMonth.plusMonths(it.toLong()) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(months) { month ->
            CalenderScreen(month = month)
        }
    }
}

@Composable
fun CalenderScreen(month: YearMonth) {
    val firstDayOfMonth = month.atDay(1)
    val startOffset = firstDayOfMonth.dayOfWeek.value - 1 // Wochentag (1 = Montag)
    val daysInMonth = month.lengthOfMonth()

    Column(modifier = Modifier.padding(16.dp)) {
        // Monats-Header
        Text(
            text = "${month.month.name} ${month.year}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Wochentage (Mo-So)
        Row(modifier = Modifier.fillMaxWidth()) {
            val daysOfWeek = listOf("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So")
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    fontWeight = Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tage des Monats
        androidx.compose.foundation.layout.BoxWithConstraints {
            val itemWidth = maxWidth / 7

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                // Leere Felder bis zum ersten Tag des Monats
                repeat(startOffset) {
                    Spacer(modifier = Modifier.width(itemWidth))
                }

                // Tage 1 bis Monatsende
                (1..daysInMonth).forEach { day ->
                    Text(
                        text = day.toString(),
                        modifier = Modifier
                            .width(itemWidth)
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun CalenderScreenPreview() {
    // Use Theme here
    CalenderScreen(month = YearMonth.now())
}