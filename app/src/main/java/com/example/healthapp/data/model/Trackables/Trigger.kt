package com.example.healthapp.data.model.Trackables
data class Trigger(
    override val name: String = "Auslöser",
    val triggers: List<String> = emptyList()
): TrackingField

