package com.example.healthapp.shared.data.termassignment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlin.time.Instant

@Dao
interface TermAssignmentDao {
    /* Gibt alle bisher bekannten Zuordnungen zu einem Text, die am häufigsten genutzte zuerst
    0 Treffer unbekannt, 1 Treffer eindeutig, mehrere mehrdeutig
    */
    @Query("SELECT * FROM term_assignment WHERE term = :term ORDER BY useCount DESC")
    suspend fun findByTerm(term: String):List<TermAssignmentEntity>

    // Neuer Begriff wird gelernt, Rückgabe ist die Zeilennummer der neuen Zeile oder -1, wenn das Paar schon vorher da war
    @Insert
    suspend fun insertIfAbsent(assignment: TermAssignmentEntity): Long

    //Bekannte Zuordnung wird erneut gewählt, der Zähler steigt um 1
    @Query("UPDATE term_assignment SET useCount = useCount + 1, lastUsedAt = :usedAt WHERE term = :term AND definitionId = :definitionId")
    suspend fun registerUse(term: String, definitionId: String, usedAt: Instant)

}

