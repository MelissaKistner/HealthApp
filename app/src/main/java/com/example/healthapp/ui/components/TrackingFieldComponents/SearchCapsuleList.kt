package com.example.healthapp.ui.components.TrackingFieldComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SearchCapsuleList(
    items: List<String>,
    selectedItems: List<String>,
    onItemClick: (String) -> Unit,
    onAddItem: (String) -> Unit,
    modifier: Modifier = Modifier,
    searchLabel: String = "Suchen oder hinzufügen",
    maxResultHeight: Dp = 300.dp
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }

    val input = query.trim()

    val filtered = remember(items, input) {
        if (input.isBlank()) {
            items
        } else {
            items.filter { item ->
                item.contains(
                    other = input,
                    ignoreCase = true
                )
            }
        }
    }

    val exactMatchExists = remember(items, input) {
        items.any { item ->
            item.equals(
                other = input,
                ignoreCase = true
            )
        }
    }

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { newValue ->
                query = newValue
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(searchLabel)
            },
            singleLine = true,
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = { query = "" }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Eingabe löschen"
                        )
                    }
                }
            }
        )

        if (input.isNotEmpty() && !exactMatchExists) {
            OutlinedButton(
                onClick = {
                    onAddItem(input)
                    query = ""
                },
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )

                Spacer(Modifier.width(8.dp))

                Text("„$input“ hinzufügen")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxResultHeight)
                .verticalScroll(rememberScrollState())
                .padding(top = 12.dp)
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filtered.forEach { item ->
                    val isSelected = item in selectedItems
                    val label = "$item ${if (isSelected) "−" else "+"}"

                    ListCapsule(
                        label = label,
                        onClick = {
                            onItemClick(item)
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SearchCapsuleListPreview() {
    // Use Theme here
    SearchCapsuleList(
        items = TODO(),
        selectedItems = TODO(),
        onItemClick = TODO(),
        onAddItem = TODO(),
        modifier = TODO(),
        searchLabel = TODO(),
        maxResultHeight = TODO()
    )
}