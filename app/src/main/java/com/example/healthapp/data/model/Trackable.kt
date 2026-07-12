package com.example.healthapp.data.model

data class Trackable(
    val id: Int,
    val name: String,
    val trackingFields: List<TrackingField>
)
