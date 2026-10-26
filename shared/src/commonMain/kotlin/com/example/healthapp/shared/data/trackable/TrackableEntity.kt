package com.example.healthapp.shared.data.trackable

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlin.time.Instant

/**
 * Ein Trackable gruppiert Events (wie ein Tagebuch) und hat selbst keine Felder.
 *
 * @property id Stabile ID (UUID). System-Trackables wie "Sonstiges" haben eine feste, im Code bekannte ID.
 * @property isSystem `true` für vorgegebene Einträge, die nicht gelöscht werden sollen.
 * @property archivedAt Zeitpunkt der Archivierung, `null` bedeutet aktiv. Archivierte Trackables
 *   werden nur ausgeblendet, ihre Daten bleiben erhalten. Beim Zurücknehmen wieder auf `null` setzen.
 * @property updatedAt Zeitpunkt der letzten Änderung, für den späteren Abgleich zwischen Geräten.
 */
@Entity(tableName = "trackables")
data class TrackableEntity(
    @PrimaryKey val id: String,
    val name: String,
    val isSystem: Boolean,
    val archivedAt: Instant? = null,
    val updatedAt: Instant
) {
    /**
     * Berechnet aus `archivedAt`, ob das Trackable archiviert ist. Die Eigenschaft wird nicht
     * in der Datenbank gespeichert (`@get:Ignore`), damit es nur eine Quelle der Wahrheit gibt.
     */
    @get:Ignore
    val isArchived: Boolean
        get() = archivedAt != null
}
