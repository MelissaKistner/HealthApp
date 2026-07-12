package com.example.healthapp.data.model

data class Symptoms(
    override val name: String = "Symptome",
    val symptoms: List<String> = emptyList()
): TrackingField
