package com.example.healthapp.data.model.Trackables

import com.example.healthapp.data.enums.Effect

data class Drugs(
    val names: List<String> = emptyList(),
    val effect: Effect
)
