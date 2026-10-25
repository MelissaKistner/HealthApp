package com.example.healthapp.shared.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE_FILE_NAME = "healthapp.db"

/**
 * Android-spezifischer Teil: Hier wird festgelegt, wo die Datenbankdatei liegt.
 */
fun getHealthDatabaseBuilder(context: Context): RoomDatabase.Builder<HealthDatabase> {
    val appContext = context.applicationContext
    val databaseFile = appContext.getDatabasePath(DATABASE_FILE_NAME)
    return Room.databaseBuilder<HealthDatabase>(
        context = appContext,
        name = databaseFile.absolutePath
    )
}
