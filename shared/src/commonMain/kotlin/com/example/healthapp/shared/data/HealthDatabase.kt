package com.example.healthapp.shared.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.healthapp.shared.data.event.EventDao
import com.example.healthapp.shared.data.event.EventDefinitionEntity
import com.example.healthapp.shared.data.event.EventTypeConverters
import com.example.healthapp.shared.data.quicknote.QuickNoteDao
import com.example.healthapp.shared.data.quicknote.QuickNoteEntity

@Database(
    entities = [QuickNoteEntity::class, EventDefinitionEntity::class],
    version = 2
)
@TypeConverters(DateTimeConverters::class, EventTypeConverters::class)
@ConstructedBy(HealthDatabaseConstructor::class)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun quickNoteDao(): QuickNoteDao
    abstract fun eventDao(): EventDao
}

// Auf jeder Plattform kann man die Datenbank nicht direkt erzeugen, daher gibt es
// diese `expect`-Deklaration. Den passenden `actual`-Teil erzeugt der Room-Compiler selbst.
@Suppress("KotlinNoActualForExpect")
expect object HealthDatabaseConstructor : RoomDatabaseConstructor<HealthDatabase> {
    override fun initialize(): HealthDatabase
}

/**
 * Baut die Datenbank aus einem plattformspezifischen Builder.
 * Nur der Builder (wo liegt die Datei?) unterscheidet sich je Plattform,
 * alles andere ist für Android und iOS gleich.
 */
fun createHealthDatabase(builder: RoomDatabase.Builder<HealthDatabase>): HealthDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .build()
}
