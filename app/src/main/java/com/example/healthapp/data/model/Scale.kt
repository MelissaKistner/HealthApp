package com.example.healthapp.data.model

data class Scale(
    val name: String = "Skala",
    val min: Int = 0,
    val max: Int = 10,
): TrackingField


