package com.example.healthapp.data.enums

/**
 * TODO auslagern stringres für Mehrsprachigkeit
 *
 * @property screenname
 */
enum class Effect(val screenname: String) {
    NONE("keine"), LIGHT("leicht"), MEDIUM("mittel"), STRONG("gut"), VERY_STRONG("sehr gut")
}