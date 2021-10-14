package com.los3molineros.laligasantander.data.remote

import com.los3molineros.laligasantander.data.model.SeasonResponse

class SeasonDataSource(private val webservice: Webservice) {
    suspend fun getSeasonId(leagueId: Int): SeasonResponse = webservice.getSeasonId(league_id = leagueId)
}