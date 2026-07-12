
package com.example.healthapp.data.model


data class Medicine(
    override val name: String = "Medikament",
    val drugs: List<String> = emptyList()
): TrackingField
