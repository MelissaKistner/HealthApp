package com.example.healthapp.data.model.Trackables

data class Symptoms(
    override val name: String = "Symptome",
    val symptoms: List<String> = emptyList()
): TrackingField
