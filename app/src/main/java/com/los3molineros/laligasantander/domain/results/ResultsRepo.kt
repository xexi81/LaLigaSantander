package com.los3molineros.laligasantander.domain.results

import com.los3molineros.laligasantander.data.model.Match
import com.los3molineros.laligasantander.data.model.MatchFirestore

interface ResultsRepo {
    suspend fun getResults(round: Int): List<MatchFirestore>
    suspend fun getActualRound(): Int
    suspend fun getMaxRound(): Int
}