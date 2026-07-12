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
import com.example.healthapp.data.enums.Effect
import com.example.healthapp.data.enums.FunctionalImpactCategory
import com.example.healthapp.data.enums.Impact

/**
 * TODO Scalefield nutzen mit besseren begriffen?
 * TODO ImpactScale verschönern
 * @param modifier
 */
@Composable
fun FunctionalImpactField(
    modifier: Modifier = Modifier
) {
    Column(
        modifier.border(
            width = 2.dp,
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.tertiary
        ).padding(16.dp)
    ) {
        Text("Beeinträchtigungen", style = MaterialTheme.typography.headlineSmall)
        Column() {
            FunctionalImpactCategory.entries.forEach { category ->
                Text(category.screenName, style = MaterialTheme.typography.titleLarge)
                ImpactScale(onSelected = {})
                //ScaleField(steps = Impact.entries.map { it.screenname })
            }


        }

    }
}

@Preview(showBackground = true)
@Composable
private fun FunctionalImpactFieldPreview() {
    // Use Theme here
    FunctionalImpactField()
}

