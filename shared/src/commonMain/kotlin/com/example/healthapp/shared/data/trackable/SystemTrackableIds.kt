package com.example.healthapp.shared.data.trackable

/**
 * System-IDs: Keinesfalls ändern, bestehende Datenbankeinträge greifen auf diese IDs zu
 * bei weiteren System-Trackables (Standardkrankheiten/Themen, die vorangelegt sein sollen),
 * hier aufsteigend weitere IDs vergeben
 * Garantiert, dass keine UUIDs doppelt vergeben werden
 */
object SystemTrackableIds {
    const val OTHER = "00000000-0000-0000-0000-000000000001"

}