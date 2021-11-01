package com.los3molineros.laligasantander.domain.matchDetail

import com.los3molineros.laligasantander.data.model.MatchResultResponse

interface MatchDetailRepo {
    suspend fun getMatchById(matchId: Int, ftScore: String?): MatchResultResponse
}