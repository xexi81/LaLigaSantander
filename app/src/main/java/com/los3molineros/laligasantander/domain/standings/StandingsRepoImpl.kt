package com.los3molineros.laligasantander.domain.standings

import com.los3molineros.laligasantander.common.DateClass
import com.los3molineros.laligasantander.data.firestore.FirestoreParams
import com.los3molineros.laligasantander.data.model.Params
import com.los3molineros.laligasantander.data.model.StandingsUI
import com.los3molineros.laligasantander.data.remote.ApiDataSource

class StandingsRepoImpl(
    private val dataWSSource: ApiDataSource,
    private val firestoreParams: FirestoreParams
) : StandingsRepo {


    override suspend fun getStandings(): List<StandingsUI> {
        // Firestore params must exist
        val params = firestoreParams.getParams() ?: throw Exception("No data")
        val seasonId = params.seasonId ?: throw Exception("No data")

        val standingsUIList: MutableList<StandingsUI> = mutableListOf()

        getStandings(params)

        val standingsResponse = firestoreParams.getStandings(seasonId)
        standingsResponse?.standings?.let {
            for (standing in it) {

                val team = firestoreParams.getTeam(standing.team_id)
                val standingsUI = StandingsUI(standing, team)
                standingsUIList.add(standingsUI)
            }
        }
        return standingsUIList
    }


    private suspend fun getStandings(params: Params) {
        // If we never search for standings before, we search it
        if (params.lastStandingCheck == null) {
            searchStandings(params.seasonId)
        }

        // We search for standings if it pass too many time from last searched
        params.lastStandingCheck?.let {
            if (DateClass().minutesBetweenNowAndDate(it) > params.standingsCheckTime) {
                searchStandings(params.seasonId)
            }
        }
    }

    private suspend fun searchStandings(seasonId: Int?) {
        if (seasonId == null) {
            throw Exception("No data")
        }

        val dataStandings = dataWSSource.getStandings(seasonId).dataStandings

        // Delete standingList from firestore with document = seasonId
        firestoreParams.deleteStandings(seasonId)

        // Write standingList with document = seasonId
        firestoreParams.writeStandings(seasonId, dataStandings)

        // Update lastStandingCheck from params
        firestoreParams.updateStandingCheck()
    }

}