
package com.example.healthapp.data.model

import com.example.healthapp.data.enums.Effect


data class Effectiveness(
    override val name: String = "Wirkung",
    val effect: List<Effect> = listOf(Effect.NONE, Effect.LIGHT, Effect.MEDIUM, Effect.STRONG, Effect.VERY_STRONG)
): TrackingField
