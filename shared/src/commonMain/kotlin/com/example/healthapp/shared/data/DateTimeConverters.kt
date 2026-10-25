package com.example.healthapp.shared.data

import androidx.room.TypeConverter
import kotlin.time.Instant

/**
 * Room kann nur einfache Typen (Zahlen, Text) speichern. Ein Type Converter
 * erklärt Room, wie ein eigener Typ in so einen einfachen Typ übersetzt wird
 * und wieder zurück.
 *
 * Ein Instant ist ein exakter Zeitpunkt, unabhängig von Zeitzone und Uhrzeit-Anzeige.
 * Gespeichert wird er als Millisekunden seit 1.1.1970 (UTC).
 */
object DateTimeConverters {

    @TypeConverter
    fun instantToEpochMillis(instant: Instant): Long {
        return instant.toEpochMilliseconds()
    }

    @TypeConverter
    fun epochMillisToInstant(epochMillis: Long): Instant {
        return Instant.fromEpochMilliseconds(epochMillis)
    }
}
