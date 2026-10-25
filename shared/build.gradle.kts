import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// Geteiltes Modul: Datenmodelle, Room-Datenbank und Fachlogik.
// Keine Oberfläche. Die UI bleibt vorerst im Modul :app.
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
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
}
