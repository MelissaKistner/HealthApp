package com.example.healthapp.ui.components.TrackingFieldComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PainField(
    modifier: Modifier = Modifier
) {

        ScaleField(steps = (1..10).map { it.toString()}, title = "Schmerzintensität")

}

@Preview(showBackground = true)
@Composable
private fun PainFieldPreview() {
    // Use Theme here
    PainField()
}