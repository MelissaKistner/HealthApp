package com.example.healthapp.ui.components.TrackingFieldComponents

import android.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthapp.data.enums.Impact
import java.time.format.TextStyle

@Composable
fun FreeTextField(
    modifier: Modifier = Modifier
) {
    Column(modifier.border(width = 2.dp, shape = RoundedCornerShape(15.dp), color = MaterialTheme.colorScheme.tertiary).padding(16.dp)) {
        Text("Informationen", style = MaterialTheme.typography.headlineSmall)
        TextField(
            state = rememberTextFieldState("..."),
            lineLimits = TextFieldLineLimits.MultiLine(maxHeightInLines = 6),
            label = { Text("Gib hier zusätzliche Informationen ein") },

        )

    }
}

@Preview(showBackground = true)
@Composable
private fun FreeTextFieldPreview() {
    // Use Theme here
    FreeTextField()
}