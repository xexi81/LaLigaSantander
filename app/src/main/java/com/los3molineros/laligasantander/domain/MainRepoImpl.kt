package com.los3molineros.laligasantander.domain

import com.los3molineros.laligasantander.common.DateClass
import com.los3molineros.laligasantander.data.firestore.FirestoreParams
import com.los3molineros.laligasantander.data.model.Params
import com.los3molineros.laligasantander.data.remote.ApiDataSource

class MainRepoImpl(
    private val dataWSSource: ApiDataSource,
    private val firestoreParams: FirestoreParams
) : MainRepo {
    override suspend fun getData(): Boolean {
        // Firestore params must exist
        val params = firestoreParams.getParams() ?: throw Exception("No data")

        // SeasonID
        params.seasonId = getSeasonID(params)

        // Standings
        getStandings(params)

        // getTeams
        getTeams(params)

        // getMatchs
        getMatches(params)

        return true
    }


    private suspend fun getSeasonID(params: Params): Int {
        // If we never search for a season before, we search it
        if (params.lastSeasonCheck == null || params.seasonId == null) {
            return searchSeason(params.leagueId)
        }

        // We search seasonId if there pass too many time from last searched
        if (DateClass().minutesBetweenNowAndDate(params.lastSeasonCheck) > params.seasonCheckTime) {
            return searchSeason(params.leagueId)
        }

        return params.seasonId!!
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

    private suspend fun getTeams(params: Params) {
        params.seasonId?.let {
            val standings = firestoreParams.getStandings(it)
            standings?.let { dataStandings ->
                for (teamStanding in dataStandings.standings) {
                    val team = firestoreParams.getTeam(teamStanding.team_id)
                    if (team==null) {
                        val teamResponse = dataWSSource.getTeam(teamStanding.team_id)
                        if (teamResponse.team.team_id == teamStanding.team_id) {
                            firestoreParams.writeTeam(teamResponse.team)
                        }
                    }
                }
            }
        }
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


    private suspend fun searchSeason(leagueId: Int): Int {
        val seasonResponse = dataWSSource.getSeasonId(leagueId)

        val seasonList = seasonResponse.seasonList.filter { it.is_current == 1 }

        if (seasonList.isEmpty()) {
            throw Exception("No data")
        }

        firestoreParams.writeSeason(seasonList[0].season_id)
        return seasonList[0].season_id
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