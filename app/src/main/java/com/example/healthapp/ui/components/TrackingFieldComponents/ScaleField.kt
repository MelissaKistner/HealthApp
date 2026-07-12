package com.example.healthapp.ui.components.TrackingFieldComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ScaleField(
    modifier: Modifier = Modifier,
    steps: List<String>,
    title: String
) {


    // Zustand für den Slider (als Float, da der Compose Slider ein Float benötigt)
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column(
        modifier.border(
            width = 2.dp,
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.tertiary
        ).padding(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Der Slider
            Slider(
                value = sliderPosition,
                onValueChange = { newValue -> sliderPosition = newValue },
                valueRange = 0f..((steps.size - 1).toFloat()),
                // Anzahl der Stufen entspricht der Anzahl der Textelemente
                steps = steps.size - 2
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                steps.forEachIndexed { index, text ->
                    Text(
                        text = text,
                        // Macht jeden Textbereich gleich groß für perfekte Ausrichtung
                        modifier = Modifier.weight(1f),
                        // Richtet den Text passend zu seiner Position aus
                        textAlign = when (index) {
                            0 -> TextAlign.Start
                            steps.size - 1 -> TextAlign.End
                            else -> TextAlign.Center
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Den gerundeten Float-Wert als Index nutzen, um den Text anzuzeigen
            val currentText = steps[sliderPosition.toInt()]
            Text(text = "Ausgewählt: $currentText")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScaleFieldPreview() {
    // Use Theme here
    ScaleField(steps = listOf("keine", "niedrig", "mittel", "stark"), title = "Schmerzen")
}