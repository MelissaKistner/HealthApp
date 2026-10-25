package com.example.healthapp.shared.time

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

/**
 * Bestimmt, an welchem Kalendertag ein Zeitpunkt für die Nutzerin liegt.
 *
 * Die Zeitzone ist wichtig: Ein Ereignis um 23:30 Uhr deutscher Zeit liegt in UTC
 * schon am nächsten Tag. Im Kalender soll es aber am Tag erscheinen, an dem es
 * die Nutzerin erlebt hat. Deshalb nehmen wir standardmäßig die Zeitzone des Geräts.
 */
fun Instant.toLocalDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return toLocalDateTime(timeZone).date
}
