package com.example.healthapp.shared.data

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

private const val DATABASE_FILE_NAME = "healthapp.db"

/**
 * iOS-spezifischer Teil: Hier wird festgelegt, wo die Datenbankdatei liegt.
 * Auf iOS ist das der "Documents"-Ordner der App.
 */
fun getHealthDatabaseBuilder(): RoomDatabase.Builder<HealthDatabase> {
    val databasePath = documentDirectoryPath() + "/" + DATABASE_FILE_NAME
    return Room.databaseBuilder<HealthDatabase>(name = databasePath)
}

// ExperimentalForeignApi: Der Zugriff auf Apple-Systemfunktionen aus Kotlin
// (sogenannte C-Interop) ist als experimentell markiert.
@OptIn(ExperimentalForeignApi::class)
private fun documentDirectoryPath(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    return requireNotNull(documentDirectory?.path) {
        "Documents-Ordner konnte nicht ermittelt werden"
    }
}
