package com.example.healthapp.ui.components.TrackingFieldComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.sp
import kotlin.collections.plus

/**
 * TODO Kapsel schlner gestalten
 * TODO Hinzufügen zum Speichern
 * TODO Liste an VM anschließen
 */
@Composable
fun SymptomsField(
    modifier: Modifier = Modifier,
    symptoms: List<String> = emptyList(),
    title: String
) {
    var actualSymptoms by rememberSaveable {
        mutableStateOf(emptyList<String>())
    }

    Column(
        modifier = modifier
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(15.dp),
                color = MaterialTheme.colorScheme.tertiary
            )
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )

        SearchCapsuleList(
            items = symptoms,
            selectedItems = actualSymptoms,
            searchLabel = "Symptome suchen oder hinzufügen",
            onItemClick = { symptom ->
                actualSymptoms =
                    if (symptom in actualSymptoms) {
                        actualSymptoms - symptom
                    } else {
                        actualSymptoms + symptom
                    }
            },
            onAddItem = { newItem ->
                // viewModel.addSymptom(newItem)
                actualSymptoms += newItem
            }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SymptomsFieldPreview() {
    // Use Theme here
    SymptomsField(title = "Symptome", symptoms = listOf("Kopfschmerzen", "Halsschmerzen", "taube Hände", "Aura"))
}