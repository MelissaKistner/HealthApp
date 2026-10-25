package com.example.healthapp.shared.data.quicknote

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

/**
 * Eine Schnellnotiz: Text der Nutzerin plus Zeitpunkt der Eingabe.
 *
 * Sie ist noch kein Event. Bei der Zuordnung wird daraus ein Event
 * und die QuickNote wird gelöscht (siehe docs/PROJECT_VISION.md, Abschnitt 17).
 *
 * @property id Stabile, eindeutige ID als Text (später eine UUID). Anders als eine
 * hochzählende Zahl bleibt sie auch beim Abgleich zwischen Geräten eindeutig.
 * @property text Der eingegebene Text, z. B. "Kopfschmerzen".
 * @property enteredAt Zeitpunkt der Eingabe.
 * @property updatedAt Zeitpunkt der letzten Änderung (Vorsorge für späteren Abgleich).
 */
@Entity(tableName = "quick_notes")
data class QuickNoteEntity(
    @PrimaryKey val id: String,
    val text: String,
    val enteredAt: Instant,
    val updatedAt: Instant
)
