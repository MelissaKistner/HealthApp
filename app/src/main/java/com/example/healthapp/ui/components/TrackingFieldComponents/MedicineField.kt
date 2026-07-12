package com.example.healthapp.ui.components.TrackingFieldComponents

import android.R
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthapp.data.enums.Effect
import kotlin.collections.mutableListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.healthapp.data.enums.Impact
import kotlin.collections.emptyList

/**
 * TODO Icon statt String für plus und minus
 */

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MedicineField(
    modifier: Modifier = Modifier,
    drugs: List<String>,

) {
    var usedDrugs by remember { mutableStateOf(emptyList<String>()) }
    Column(
        modifier.border(
            width = 2.dp,
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.tertiary
        ).padding(16.dp)
    ) {
        Text("Medikamente", style = MaterialTheme.typography.headlineSmall)
        LazyRow() {
            items(drugs) {drug ->
                val label = drug + if (usedDrugs.contains(drug)) " -" else " +"
                Text(label, modifier.padding(8.dp).background(color = MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(40.dp)).padding(8.dp).clickable(onClick = {if (usedDrugs.contains(drug)) usedDrugs = usedDrugs.filter { it != drug } else usedDrugs += drug}), color = MaterialTheme.colorScheme.onTertiary)

            }

        }
        Text("Benutzt:", style = MaterialTheme.typography.bodyMedium)
        usedDrugs.forEach { drug ->
            Text(drug, modifier.padding(8.dp).background(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(40.dp)).padding(8.dp) )
        }
//        EffectivenessField(onSelected = {})
        if (usedDrugs.isNotEmpty()) {
            ScaleField(steps = Effect.entries.map { it.screenname }, title = "Wirkung")
        }


    }
}

@Preview(showBackground = true)
@Composable
private fun MedicineFieldPreview() {
    // Use Theme here
    MedicineField(drugs = listOf("Sumatriptan", "Novalgin", "Ibuprofen"))
}