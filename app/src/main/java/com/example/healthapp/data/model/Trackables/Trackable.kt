package com.example.healthapp.data.model.Trackables

data class Trackable(
    val id: Int,
    val name: String,
    val trackingFields: List<TrackingField>
)
