package com.example.healthapp.ui.components


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthapp.data.model.Effectiveness
import com.example.healthapp.data.model.FunctionalImpact
import com.example.healthapp.data.model.Medicine
import com.example.healthapp.data.model.Scale
import com.example.healthapp.data.model.Symptoms
import com.example.healthapp.data.model.FreeText
import com.example.healthapp.data.model.TrackingField
import com.example.healthapp.data.model.Trigger
import com.example.healthapp.data.model.YesNo


@Composable
fun TrackingField(
    modifier: Modifier = Modifier,
    field: TrackingField
) {
    when (field) {
        is Effectiveness -> TODO()
        is FunctionalImpact -> TODO()
        is Medicine -> TODO()
        is Scale -> TODO()
        is Symptoms -> TODO()
        is FreeText -> TODO()
        is Trigger -> TODO()
        is YesNo -> TODO()
        else -> TODO()
    }

}

@Preview(showBackground = true)
@Composable
private fun TrackingFieldPreview() {
    // Use Theme here
    TrackingField(field = TrackingField.Symptoms, modifier = Modifier)
}