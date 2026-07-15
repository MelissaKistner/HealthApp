package com.example.healthapp.ui.trackables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthapp.data.mock.Mock
import com.example.healthapp.data.model.Trackables.Trackable
import com.example.healthapp.ui.components.TrackingFieldAssignment

@Composable
fun TrackableScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit,
    trackableName: String
) {
    val trackable = Mock.trackables.filter { it.name == trackableName }.first()
    LazyColumn() {
        item {
            Text(trackable.name)
        }

        trackable.trackingFields.forEach { field ->
            item {
                TrackingFieldAssignment(field = field, title = field.name)
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun TrackableScreenPreview() {
    // Use Theme here
    TrackableScreen(onNavigateBack = {}, onSave = {}, trackableName = "Migräne")
}