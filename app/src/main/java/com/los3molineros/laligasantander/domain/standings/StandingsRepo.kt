package com.los3molineros.laligasantander.domain.standings

import com.los3molineros.laligasantander.data.model.StandingsUI

interface StandingsRepo {
    suspend fun getStandings(): List<StandingsUI>
}