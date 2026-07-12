package com.example.healthapp.data.model

data class YesNo(
    override val name: String = "Ja-/Nein-Auswahl",
    val onCheckedToggle: () -> Unit = {},
    val toggleFunction: String = ""
): TrackingField
