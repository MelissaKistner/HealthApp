package com.example.healthapp.ui.components.TrackingFieldComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthapp.data.enums.Effect
import com.example.healthapp.data.enums.Impact

@Composable
fun ImpactScale(
    modifier: Modifier = Modifier,
    onSelected: (Impact) -> Unit
) {
    var selected by remember { mutableStateOf(Impact.NONE.screenname) }
    LazyColumn(modifier.height(90.dp)) {
        Impact.entries.forEach { impact ->
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelected(impact)
                            selected = impact.screenname
                        }
                        .padding(vertical = 8.dp)
                ) {
                    RadioButton(selected == impact.screenname, {
                        onSelected(impact)
                    }
                    )

                    Text(
                        text = impact.screenname
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ImpactScalePreview() {
    // Use Theme here
    ImpactScale(onSelected = {})
}