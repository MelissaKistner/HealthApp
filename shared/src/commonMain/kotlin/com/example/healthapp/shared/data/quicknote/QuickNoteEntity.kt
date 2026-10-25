package com.example.healthapp.shared.data.quicknote

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Eine Schnellnotiz: Text der Nutzerin plus Zeitpunkt der Eingabe.
 *
 * Sie ist noch kein Event. Bei der Zuordnung wird daraus ein Event
 * und die QuickNote wird gelöscht (siehe docs/PROJECT_VISION.md, Abschnitt 17).
 *
 * Zeitpunkte sind vorerst als Millisekunden seit 1.1.1970 (UTC) gespeichert.
 * Das ist ein einfacher Zahlenwert, den Room ohne Umwandlung ablegen kann.
 * Wie wir daraus lesbare Datums-Typen machen, entscheiden wir im nächsten Schritt.
 *
 * @property id Stabile, eindeutige ID als Text (später eine UUID). Anders als eine
 * hochzählende Zahl bleibt sie auch beim Abgleich zwischen Geräten eindeutig.
 * @property text Der eingegebene Text, z. B. "Kopfschmerzen".
 * @property enteredAtEpochMillis Zeitpunkt der Eingabe.
 * @property updatedAtEpochMillis Zeitpunkt der letzten Änderung (Vorsorge für späteren Abgleich).
 */
@Entity(tableName = "quick_notes")
data class QuickNoteEntity(
    @PrimaryKey val id: String,
    val text: String,
    val enteredAtEpochMillis: Long,
    val updatedAtEpochMillis: Long
)
