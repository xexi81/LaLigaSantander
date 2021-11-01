package com.los3molineros.laligasantander.domain.results

import android.content.Context
import com.los3molineros.laligasantander.common.DateClass
import com.los3molineros.laligasantander.data.firestore.FirestoreParams
import com.los3molineros.laligasantander.data.model.MatchFirestore
import com.los3molineros.laligasantander.data.model.Params
import com.los3molineros.laligasantander.data.remote.ApiDataSource

class ResultsRepoImpl(private val dataWSSource: ApiDataSource, private val firestoreParams: FirestoreParams, private val context: Context) :
    ResultsRepo {


    override suspend fun getMatches() {
        // Firestore params must exist
        val params = firestoreParams.getParams() ?: throw Exception("No data")

        // getMatchs
        getMatches(params)
    }

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

    private suspend fun getMatches(params: Params) {
        if (params.lastMatchCheck == null) {
            searchMatches(params.seasonId)
        }

        params.lastMatchCheck?.let {
            if (DateClass().minutesBetweenNowAndDate(it) > params.matchesCheckTime) {
                searchMatches(params.seasonId)
            }
        }
    }

    private suspend fun searchMatches(seasonId: Int?) {
        if (seasonId == null) {
            throw Exception("No data")
        }

        val dataMatches = dataWSSource.getMatches(seasonId)

        // Delete Matches from firestore with document = seasonId
        firestoreParams.deleteMatches(seasonId)

        // Write matches with document = seasonId
        firestoreParams.writeMatches(seasonId, dataMatches)

        // Update lastMatchCheck from params
        firestoreParams.updateMatchCheck()
    }
}