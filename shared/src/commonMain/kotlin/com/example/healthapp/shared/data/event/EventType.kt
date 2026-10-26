package com.example.healthapp.shared.data.event

/**
 * Die Art einer Eventdefinition. Auswertungen hängen daran (z. B. die Medikamenten-Einnahmetage),
 * deshalb ist es ein fester Enum im Code und keine frei wählbare Angabe.
 *
 * Die Namen werden als Text in der Datenbank gespeichert und dürfen nicht mehr umbenannt werden.
 * Anzeigetexte stehen bewusst nicht hier, sondern in der Oberfläche (Mehrsprachigkeit).
 */
enum class EventType {
    /** Beschwerde oder Symptom. */
    SYMPTOM,

    /** Medikamenteneinnahme. */
    MEDICATION,

    /** Beobachtung oder Veränderung. */
    OBSERVATION,

    /** Neutraler möglicher Einflussfaktor, ohne Aussage über eine Ursache. */
    EXPOSURE,

    /** Messwert, z. B. Blutdruck. */
    MEASUREMENT,

    /** Körperlicher Vorgang, z. B. Menstruation. */
    BODILY_PROCESS,

    /** Aktivität oder Belastung. */
    ACTIVITY,

    /** Schlaf oder Erholung. */
    SLEEP_RECOVERY,

    /** Allgemeiner, nicht weiter spezifizierter Eintrag. */
    GENERAL
}
