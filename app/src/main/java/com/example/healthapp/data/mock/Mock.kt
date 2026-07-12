package com.example.healthapp.data.mock

import com.example.healthapp.data.model.Trackables.Effectiveness
import com.example.healthapp.data.model.Trackables.FreeText
import com.example.healthapp.data.model.Trackables.FunctionalImpact
import com.example.healthapp.data.model.Trackables.Medicine
import com.example.healthapp.data.model.Trackables.Scale
import com.example.healthapp.data.model.Trackables.Symptoms
import com.example.healthapp.data.model.Trackables.Trackable
import com.example.healthapp.data.model.Trackables.TrackingField
import com.example.healthapp.data.model.Trackables.Trigger



object Mock {
    val trackables: List<Trackable> = listOf(
        Trackable(
            id = 1,
            name = "Migräne",
            trackingFields = listOf(
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