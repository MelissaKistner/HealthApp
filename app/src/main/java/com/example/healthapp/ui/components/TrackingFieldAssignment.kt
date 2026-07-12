package com.example.healthapp.ui.components


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.healthapp.data.model.Trackables.Effectiveness
import com.example.healthapp.data.model.Trackables.FunctionalImpact
import com.example.healthapp.data.model.Trackables.Medicine
import com.example.healthapp.data.model.Trackables.Scale
import com.example.healthapp.data.model.Trackables.Symptoms
import com.example.healthapp.data.model.Trackables.FreeText
import com.example.healthapp.data.model.Trackables.TrackingField
import com.example.healthapp.data.model.Trackables.Trigger
import com.example.healthapp.data.model.Trackables.YesNo
import com.example.healthapp.ui.components.TrackingFieldComponents.EffectivenessField
import com.example.healthapp.ui.components.TrackingFieldComponents.FreeTextField
import com.example.healthapp.ui.components.TrackingFieldComponents.FunctionalImpactField
import com.example.healthapp.ui.components.TrackingFieldComponents.MedicineField
import com.example.healthapp.ui.components.TrackingFieldComponents.ScaleField
import com.example.healthapp.ui.components.TrackingFieldComponents.SymptomsField
import com.example.healthapp.ui.components.TrackingFieldComponents.TriggerField
import com.example.healthapp.ui.components.TrackingFieldComponents.YesNoField


@Composable
fun TrackingFieldAssignment(
    modifier: Modifier = Modifier,
    field: TrackingField,
    title: String
) {
    when (field) {
        is Effectiveness -> EffectivenessField() { }
        is FunctionalImpact -> FunctionalImpactField()
        is Medicine -> MedicineField(drugs = field.drugs)
        is Scale -> ScaleField(steps = field.steps, title = field.name)
        is Symptoms -> SymptomsField(title = field.name, symptoms = field.symptoms)
        is FreeText -> FreeTextField()
        is Trigger -> TriggerField(title = field.name, triggers = field.triggers)
        is YesNo -> YesNoField(title = field.name, onCheckedToggle = field.onCheckedToggle, toggleFunction = field.toggleFunction)

    }

}

//@Preview(showBackground = true)
//@Composable
//private fun TrackingFieldAssignmentPreview() {
//    // Use Theme here
//    TrackingFieldAssignment(field = , modifier = Modifier)
//}