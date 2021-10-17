package com.los3molineros.laligasantander.data.firestore

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.los3molineros.laligasantander.common.DateClass
import com.los3molineros.laligasantander.data.model.*
import kotlinx.coroutines.tasks.await
import java.util.*

class FirestoreParams() {
    private val db = Firebase.firestore

    suspend fun getParams(): Params? {
        val result = db.collection("params").document("param").get().await()

        return result.toObject(Params::class.java)
    }

    fun writeSeason(seasonId: Int) {
        val seasonMap = mapOf("lastSeasonCheck" to DateClass().getUTCDate(), "seasonId" to seasonId)
        db.collection("params").document("param").update(seasonMap)
    }

    fun updateStandingCheck() {
        db.collection("params").document("param").update("lastStandingCheck", DateClass().getUTCDate())
    }

    fun updateMatchCheck() {
        db.collection("params").document("param").update("lastMatchCheck", DateClass().getUTCDate())
    }



    suspend fun deleteStandings(seasonId: Int) {
        db.collection("standings").document(seasonId.toString()).delete().await()
    }

    suspend fun writeStandings(seasonId: Int, dataStandings: DataStandings) {
        db.collection("standings").document(seasonId.toString()).set(dataStandings).await()
    }

    suspend fun getStandings(seasonId: Int): DataStandings? {
        val result = db.collection("standings").document(seasonId.toString()).get().await()
        return result.toObject(DataStandings::class.java)
    }



    suspend fun getTeam(team: Int): Team? {
        val result = db.collection("teams").document(team.toString()).get().await()
        return result.toObject(Team::class.java)
    }

    suspend fun writeTeam(team: Team) {
        db.collection("teams").document(team.team_id.toString()).set(team).await()
    }



    suspend fun deleteMatches(seasonId: Int) {
        db.collection("matches").document(seasonId.toString()).delete().await()
    }

    suspend fun writeMatches(seasonId: Int, response: MatchesResponse) {
        response.query.apikey = ""
        db.collection("matches").document(seasonId.toString()).set(response).await()
    }

    suspend fun getMatches(seasonId: Int): MatchesResponseFirestore? {
        val result = db.collection("matches").document(seasonId.toString()).get().await()
        return result.toObject(MatchesResponseFirestore::class.java)
    }

    suspend fun getMatchResult(matchId: Int): MatchResultResponse? {
        val result = db.collection("matchResult").document(matchId.toString()).get().await()
        return result.toObject(MatchResultResponse::class.java)
    }

    suspend fun writeMatchResult(matchId: Int, response: MatchResultResponse) {
        response.matchQuery.apikey = ""
        db.collection("matchResult").document(matchId.toString()).set(response).await()
    }

    suspend fun deleteMatchResult(matchId: Int) {
        db.collection("matchResult").document(matchId.toString()).delete().await()
    }

 }