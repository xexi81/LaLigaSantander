package com.los3molineros.laligasantander.domain.matchDetail

import com.los3molineros.laligasantander.common.CommonFunctions.debugLog
import com.los3molineros.laligasantander.data.firestore.FirestoreParams
import com.los3molineros.laligasantander.data.model.MatchResultResponse
import com.los3molineros.laligasantander.data.remote.ApiDataSource

class MatchDetailRepoImpl(
    private val dataWSSource: ApiDataSource,
    private val firestoreParams: FirestoreParams
) : MatchDetailRepo {
    override suspend fun getMatchById(matchId: Int, ftScore: String?): MatchResultResponse {
        var result = firestoreParams.getMatchResult(matchId)

        if (result == null) {
            result = dataWSSource.getMatchById(matchId)
            firestoreParams.writeMatchResult(matchId, result)
            return result
        }

        result.matchesList.let {
            if (it.status_code != 3 || it.minute!=90) {
                val newResult = dataWSSource.getMatchById(matchId)
                newResult.let { matchResult ->
                    firestoreParams.deleteMatchResult(matchId)
                    firestoreParams.writeMatchResult(matchId, matchResult)
                    return matchResult
                }
            }

            if (it.status_code == 3 && it.stats.ft_score != ftScore) {
                val newResult = dataWSSource.getMatchById(matchId)
                newResult.let { matchResult ->
                    firestoreParams.deleteMatchResult(matchId)
                    firestoreParams.writeMatchResult(matchId, matchResult)
                    return matchResult
                }
            }
        }

        return result
    }
}