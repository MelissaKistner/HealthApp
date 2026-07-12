package com.example.healthapp.data.model.Trackables

data class YesNo(
    override val name: String = "Ja-/Nein-Auswahl",
    val onCheckedToggle: () -> Unit = {},
    val toggleFunction: String = ""
): TrackingField
