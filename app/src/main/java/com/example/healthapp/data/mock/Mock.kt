package com.example.healthapp.data.mock

import androidx.compose.ui.Modifier
import com.example.healthapp.data.model.Effectiveness
import com.example.healthapp.data.model.FreeText
import com.example.healthapp.data.model.FunctionalImpact
import com.example.healthapp.data.model.Medicine
import com.example.healthapp.data.model.Scale
import com.example.healthapp.data.model.Symptoms
import com.example.healthapp.data.model.Trackable
import com.example.healthapp.data.model.TrackingField
import com.example.healthapp.data.model.Trigger



object Mock {
    val trackables: List<Trackable> = listOf(
        Trackable(
            id = 1,
            name = "Migräne",
            trackingFields = listOf<TrackingField>(
                Medicine(), Trigger(), Effectiveness(), Scale(name = "Schmerzintensität"),
                FreeText(name = "Informationen"), FunctionalImpact(), Symptoms()
            )
        ),
        Trackable(
            id = 2,
            name = "Zyklus",
            trackingFields = listOf<TrackingField>(Medicine(), Scale(name = "Schmerzintensität"), Scale(name = "Blutungsintensität"),
                FreeText(name = "Informationen"), FunctionalImpact(), Symptoms()
            )
        ),
        Trackable(
            id = 3,
            name = "Allergien",
            trackingFields = listOf<TrackingField>(Medicine(), Trigger(), Effectiveness(), Scale(name = "Intensität"),
                FreeText(name = "Informationen"), FunctionalImpact(), Symptoms()
            )
        ),
        Trackable(
            id = 4,
            name = "Neurodermitis",
            trackingFields = listOf<TrackingField>(Medicine(), Trigger(), Effectiveness(), Scale(name = "Intensität"),
                FreeText(name = "Informationen"), FunctionalImpact(), Symptoms()
            )
        )

    )
}