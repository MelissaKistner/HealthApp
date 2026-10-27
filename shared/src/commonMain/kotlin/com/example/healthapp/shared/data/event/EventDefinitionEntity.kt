package com.example.healthapp.shared.data.event

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

/**
 * Eine Eventdefinition ist ein Katalogeintrag, z. B. "Kopfschmerzen" oder "Blutdruck".
 * Sie legt fest, welchen Typ ein Event hat und welche Felder dazu erfasst werden können.
 * Die Werte selbst stehen später im Event, nicht hier.
 *
 * @property id Stabile ID (UUID).
 * @property name Name der Definition, wie ihn die Nutzerin sieht.
 * @property eventType Art der Definition, gespeichert als Text (siehe [EventTypeConverters]).
 * @property fieldsJson Felddefinitionen als JSON-Text (geordnete Liste). Das Umwandeln übernimmt später das Repository.
 * @property isPredefined `true` für Einträge aus dem mitgelieferten Start-Set, `false` für selbst angelegte.
 * @property archivedAt Zeitpunkt der Archivierung, `null` bedeutet aktiv. Archivierte Definitionen
 *   werden nur ausgeblendet, ihre Daten bleiben erhalten.
 * @property updatedAt Zeitpunkt der letzten Änderung, für den späteren Abgleich zwischen Geräten.
 */
@Entity(tableName = "event_definitions")
data class EventDefinitionEntity(
    @PrimaryKey val id: String,
    val name: String,
    val eventType: EventType,
    val fieldsJson: String,
    val isPredefined: Boolean,
    val archivedAt: Instant? = null,
    val updatedAt: Instant
)
