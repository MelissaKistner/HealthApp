package com.example.healthapp.ui.components.TrackingFieldComponents

import android.widget.ToggleButton
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * TODO speichern des Wertes
 *
 * @param modifier
 * @param title
 * @param toggleFunction
 */
@Composable
fun YesNoField(
    modifier: Modifier = Modifier,
    title: String,
    toggleFunction: String,
    onCheckedToggle: () -> Unit,
) {
    Column(
        modifier.border(
            width = 2.dp,
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.tertiary
        ).padding(16.dp)
    ) {
        var checkedState by remember { mutableStateOf(false) }
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Text(toggleFunction, style = MaterialTheme.typography.bodyMedium)
        Switch(
            checked = checkedState,
            onCheckedChange = { isChecked ->
                checkedState = isChecked
                onCheckedToggle()
            }
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun YesNoFieldPreview() {
    // Use Theme here
    YesNoField(title = "Aura", toggleFunction = "Hattest du eine Aura?") {}
}