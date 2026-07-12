package com.example.healthapp.ui.Trackables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthapp.data.mock.Mock
import com.example.healthapp.data.model.Trackables.Trackable

/**
 * TODO Navi mit Argument (Name des Trackables)
 *
 * @param modifier
 * @param trackables
 */
@Composable
fun TrackableList(
    modifier: Modifier = Modifier,
    trackables: List<Trackable>
) {
    Column() {
        trackables.forEach { trackable ->
            Text(trackable.name)

        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun TrackableListPreview() {
    // Use Theme here
    TrackableList(trackables = Mock.trackables)
}