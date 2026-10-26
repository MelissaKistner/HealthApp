package com.example.healthapp.shared.data.event

import androidx.room.TypeConverter

/**
 * Erklärt Room, wie ein [EventType] in der Datenbank gespeichert wird: als Text, z. B. "SYMPTOM".
 * Ein Text lässt sich in SQL direkt filtern (`WHERE eventType = 'MEDICATION'`).
 */
object EventTypeConverters {

    /** Enum-Wert -> Text für die Datenbank. `name` liefert immer den Namen aus dem Code. */
    @TypeConverter
    fun eventTypeToString(type: EventType): String {
        return type.name
    }

    /**
     * Text aus der Datenbank -> Enum-Wert. Gibt es keinen Wert mit diesem Namen,
     * wirft `valueOf` eine Exception. Deshalb dürfen die Namen in [EventType] nicht umbenannt werden.
     */
    @TypeConverter
    fun stringToEventType(eventString: String): EventType {
        return EventType.valueOf(eventString)
    }
}
