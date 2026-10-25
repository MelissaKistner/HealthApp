package com.example.healthapp.shared.data.event


import androidx.room.TypeConverter


object EventTypeConverters {

    @TypeConverter
    fun eventTypeToString(type: EventType): String {
        return type.name
    }

    @TypeConverter
    fun stringToEventType(eventString: String): EventType {
        return EventType.valueOf(eventString)
    }
}
