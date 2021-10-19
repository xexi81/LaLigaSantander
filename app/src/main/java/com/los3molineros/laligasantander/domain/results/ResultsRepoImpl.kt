package com.los3molineros.laligasantander.domain.results

import android.content.Context
import com.los3molineros.laligasantander.data.firestore.FirestoreParams
import com.los3molineros.laligasantander.data.model.MatchFirestore

class ResultsRepoImpl(private val firestoreParams: FirestoreParams, private val context: Context) :
    ResultsRepo {

    override suspend fun getResults(round: Int): List<MatchFirestore> {
        val seasonId = firestoreParams.getParams()?.seasonId ?: throw Exception("No data")
        val matchList = firestoreParams.getMatches(seasonId) ?: throw Exception("No data")
        return matchList.data.filter { it.round.name.toInt() == round }
    }

    override suspend fun getActualRound(): Int {
        val seasonId = firestoreParams.getParams()?.seasonId ?: throw Exception("No data")
        val matchList = firestoreParams.getMatches(seasonId) ?: throw Exception("No data")
        return matchList.data.filter { it.round._current == 1 }[0].round.name.toInt()
    }

    override suspend fun getMaxRound(): Int {
        val seasonId = firestoreParams.getParams()?.seasonId ?: throw Exception("No data")
        val matchList = firestoreParams.getMatches(seasonId) ?: throw Exception("No data")
        return matchList.data.maxOfOrNull { it.round.name.toInt() } ?: 0
    }


}