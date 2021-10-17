package com.los3molineros.laligasantander.domain.standings

import com.los3molineros.laligasantander.data.firestore.FirestoreParams
import com.los3molineros.laligasantander.data.model.StandingsUI
import java.lang.NullPointerException

class StandingsRepoImpl(private val firestoreParams: FirestoreParams): StandingsRepo {


    override suspend fun getStandings(): List<StandingsUI> {
        val seasonId = firestoreParams.getParams()?.seasonId ?: throw NullPointerException("No data")
        val standingsUIList: MutableList<StandingsUI> = mutableListOf()

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




}