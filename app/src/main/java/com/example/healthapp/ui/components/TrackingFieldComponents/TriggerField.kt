package com.example.healthapp.ui.components.TrackingFieldComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.collections.plus

/**
 * TODO StaggeredGrid auslagern? Gibt es zweimal (Symptoms)
 * TODO längere Texte plus mittig?
 * @param modifier
 * @param title
 * @param trigger
 */

@Composable
fun TriggerField(
    modifier: Modifier = Modifier,
    title: String,
    triggers: List<String>
) {
    var actualTrigger by remember { mutableStateOf(emptyList<String>()) }
    var selectedTriggers by rememberSaveable {
        mutableStateOf(emptyList<String>())
    }
    Column(
        modifier.border(
            width = 2.dp,
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.tertiary
        ).padding(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall)

        SearchCapsuleList(
            items = triggers,
            selectedItems = selectedTriggers,
            searchLabel = "Auslöser suchen",
            onItemClick = { item ->
                selectedTriggers =
                    if (item in selectedTriggers) {
                        selectedTriggers - item
                    } else {
                        selectedTriggers + item
                    }
            },
            onAddItem = { newItem ->
               // viewModel.addTrigger(newItem)
                selectedTriggers += newItem
            }
        )

    }

}

@Preview(showBackground = true)
@Composable
private fun TriggerFieldPreview() {
    // Use Theme here
    TriggerField(Modifier, "Auslöser", listOf("Hitze", "Schlafmangel", "Erdbeeren", "Alkohol", "Gräser", "Nackenbelastung", "Sellerie"))
}