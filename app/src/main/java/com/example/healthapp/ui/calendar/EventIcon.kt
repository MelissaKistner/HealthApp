package com.example.healthapp.ui.calendar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.example.healthapp.data.enums.Event

@Composable
fun EventIcon(event: Event) {
    when (event) {
        Event.MEDS -> {
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = "Schmerz"
            )
        }
        Event.UNCATEGORIZED -> {
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = "Schmerz"
            )
        }
    }
}

