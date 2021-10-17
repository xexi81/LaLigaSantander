package com.los3molineros.laligasantander.data.remote

import com.los3molineros.laligasantander.data.model.*

class ApiDataSource(private val webservice: Webservice) {
    suspend fun getSeasonId(leagueId: Int): SeasonResponse = webservice.getSeasonId(league_id = leagueId)
    suspend fun getStandings(seasonId: Int): StandingsResponse = webservice.getStandings(season_id = seasonId)
    suspend fun getTeam(teamId: Int): TeamResponse = webservice.getTeam(teamId = teamId)
    suspend fun getMatches(seasonId: Int): MatchesResponse = webservice.getMatches(season_id = seasonId)
    suspend fun getMatchById(matchId: Int): MatchResultResponse = webservice.getMatchById( matchId = matchId)
}