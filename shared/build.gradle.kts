import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// Geteiltes Modul: Datenmodelle, Room-Datenbank und Fachlogik.
// Keine Oberfläche. Die UI bleibt vorerst im Modul :app.
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.androidx.room)
}

kotlin {
    android {
        namespace = "com.example.healthapp.shared"
        compileSdk = 36
        minSdk = 26

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    // kotlin.time.Instant ist in dieser Kotlin-Version noch als experimentell markiert.
    // Dieser Eintrag erlaubt die Nutzung im ganzen Modul, statt jede Datei einzeln zu markieren.
    compilerOptions {
        optIn.add("kotlin.time.ExperimentalTime")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
            implementation(libs.kotlinx.datetime)
        }
    }
}

// Der Room-Compiler (KSP) erzeugt den Datenbankcode pro Plattform-Ziel.
// Kommt später ein Ziel dazu (z. B. iOS), braucht es hier eine weitere Zeile.
dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
}

// Room schreibt hier eine JSON-Beschreibung der Tabellen pro Datenbankversion hin.
// Sie wird für spätere Migrationen gebraucht und gehört in Git.
room {
    schemaDirectory("$projectDir/schemas")
}
