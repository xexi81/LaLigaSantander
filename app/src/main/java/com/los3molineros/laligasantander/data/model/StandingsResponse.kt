package com.los3molineros.laligasantander.data.model

import com.google.gson.annotations.SerializedName

data class StandingsResponse(
    val query: Query = Query(),
    @SerializedName("data")
    val dataStandings: DataStandings = DataStandings()
)

data class DataStandings(
    val season_id: Int = 0,
    val league_id: Int = 0,
    val has_group: Int = 0,
    val standings: List<Standing> = listOf()
)

data class Standing(
    val team_id: Int = 0,
    val position: Int = 0,
    val points: Int = 0,
    val status: String = "",
    val result: String = "",
    val overall: Overall = Overall(),
    val home: Home = Home(),
    val away: Away = Away()
)

data class Overall(
    val games_played: Int = 0,
    val won: Int = 0,
    val draw: Int = 0,
    val lost: Int = 0,
    val goals_diff: Int = 0,
    val goals_scored: Int = 0,
    val goals_against: Int = 0
)

data class Home(
    val games_played: Int = 0,
    val won: Int = 0,
    val draw: Int = 0,
    val lost: Int = 0,
    val goals_diff: Int = 0,
    val goals_scored: Int = 0,
    val goals_against: Int = 0
)

data class Away(
    val games_played: Int = 0,
    val won: Int = 0,
    val draw: Int = 0,
    val lost: Int = 0,
    val goals_diff: Int = 0,
    val goals_scored: Int = 0,
    val goals_against: Int = 0
)