package com.los3molineros.laligasantander.data.model

import com.google.gson.annotations.SerializedName


data class MatchResultResponse (
    @SerializedName("query")
    val matchQuery: TeamQuery = TeamQuery(),
    @SerializedName("data")
    val matchesList: List<MatchResult> = listOf()
)


data class MatchResult(
    val match_id: Int = 0,
    val league_id: Int = 0,
    val round: Round = Round(),
    val referee_id: Int? = null,
    val season_id: Int = 0,
    val stage: Stage = Stage(),
    val group: Group = Group(),
    val status_code: Int = 0,
    val match_start: String = "",
    val match_start_iso: String = "",
    val minute: Int = 0,
    val status: String = "",
    val stats: Stats = Stats(),
    val home_team: Home = Home(),
    val away_team: Away = Away(),
    val match_events: List<MatchEvents> = listOf()
)


data class MatchEvents(
    val team_id: Int = 0,
    val type: String? = "",
    val player_id: Int? = 0,
    val player_name: String? = "",
    val related_player_id: Int? = 0,
    val related_player_name: String? = "",
    val minute: Int = 0,
    val extra_minute: Int? = 0,
    val reason: String? = null,
    val injured: String? = null,
    val own_goal: Boolean = false,
    val penalty: Boolean = false,
    val result: String? = null
)