package com.example.healthapp.data.model.Trackables

data class Scale(
    override val name: String = "Skala",
    val min: Int = 0,
    val max: Int = 10,
    val steps: List<String> = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
): TrackingField


