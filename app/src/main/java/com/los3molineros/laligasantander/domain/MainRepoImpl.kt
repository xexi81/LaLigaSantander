package com.los3molineros.laligasantander.domain

import com.los3molineros.laligasantander.common.DateClass
import com.los3molineros.laligasantander.data.firestore.FirestoreParams
import com.los3molineros.laligasantander.data.remote.SeasonDataSource

class MainRepoImpl(
    private val dataWSSource: SeasonDataSource,
    private val firestoreParams: FirestoreParams
) : MainRepo {
    override suspend fun getSeasonId(): Int {
        // Firestore params must exist
        val params = firestoreParams.getParams() ?: throw Exception("No data")

        // If we never search for a season before, we search it
        if (params.lastSeasonCheck == null || params.seasonId == null) {
            return searchSeason(params.leagueId)
        }

        // We search seasonId if there pass too many time from last searched
        if (DateClass().minutesBetweenNowAndDate(params.lastSeasonCheck) > params.seasonCheckTime) {
            return searchSeason(params.leagueId)
        }

        return params.seasonId
    }


    private suspend fun searchSeason(leagueId: Int): Int {
        val seasonResponse = dataWSSource.getSeasonId(leagueId)

        val seasonList = seasonResponse.seasonList.filter { it.is_current == 1 }

        if (seasonList.isEmpty()) {
            throw Exception("No data")
        }

        firestoreParams.writeSeason(seasonList[0].season_id)
        return seasonList[0].season_id
    }

}