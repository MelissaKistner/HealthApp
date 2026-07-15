package com.example.healthapp.ui.trackables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthapp.data.mock.Mock
import com.example.healthapp.data.model.Trackables.Trackable

/**
 * TODO Navi mit Argument (Name des Trackables)
 * TODO Trackables aus VM holen
 * @param modifier
 * @param trackables
 */
@Composable
fun TrackableListScreen(
    modifier: Modifier = Modifier,
    trackables: List<Trackable> = Mock.trackables,
    onNavigateToTrackableScreen: (String) -> Unit
) {
    Column(modifier.fillMaxSize().wrapContentSize()) {
        trackables.forEach { trackable ->
            Card(modifier.fillMaxWidth().padding(16.dp).clickable(onClick = {onNavigateToTrackableScreen(trackable.name)})) {
                Text(trackable.name, modifier.fillMaxWidth().padding(16.dp).wrapContentSize())
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun TrackableListScreenPreview() {
    // Use Theme here
    TrackableListScreen(trackables = Mock.trackables) {}
}