package com.example.healthapp.ui.components.TrackingFieldComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthapp.data.enums.Effect

@Composable
fun EffectivenessField(
    modifier: Modifier = Modifier,
    onSelected: (Effect) -> Unit
) {
    var selected by remember { mutableStateOf(Effect.NONE) }
    Column(modifier.border(width = 2.dp, shape = RoundedCornerShape(15.dp), color = MaterialTheme.colorScheme.tertiary).padding(16.dp)) {
        Text("Wirkung", style = MaterialTheme.typography.headlineSmall)
        LazyColumn(modifier.height(90.dp)) {
            Effect.entries.forEach { effect ->
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelected(effect)
                                selected = effect
                            }
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(selected == effect, {
                            onSelected(effect)
                        }
                        )

                        Text(
                            text = effect.screenname
                        )
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EffectivenessFieldPreview() {
    // Use Theme here
    EffectivenessField(onSelected = {})
}