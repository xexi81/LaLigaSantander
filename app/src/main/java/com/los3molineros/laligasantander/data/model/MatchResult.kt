package com.los3molineros.laligasantander.data.model

import com.google.gson.annotations.SerializedName


data class MatchResultResponse (
    @SerializedName("query")
    val matchQuery: TeamQuery = TeamQuery(),
    @SerializedName("data")
    val matchesList: MatchResult = MatchResult()
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
    val home_team: Team = Team(),
    val away_team: Team = Team(),
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
    val result: String? = null,
    val match_statistics: List<MatchStatistics> = listOf()
)

data class MatchStatistics(
    val team_id: Int = 0,
    val team_name: String = "",
    val fouls: Int = 0,
    val injuries: Int = 0,
    val corners: Int  = 0,
    val offsides: Int = 0,
    val shots_total: Int = 0,
    val shots_on_target: Int = 0,
    val shots_off_target: Int = 0,
    val shots_blocked: Int = 0,
    val possessiontime: Int = 0,
    val possessionpercent: Int = 0,
    val yellowcards: Int = 0,
    val yellowredcards: Int = 0,
    val redcards: Int = 0,
    val substitutions: Int = 0,
    val goal_kick: Int = 0,
    val goal_attempts: Int = 0,
    val free_kick: Int = 0,
    val throw_in: Int = 0,
    val ball_safe: Int = 0,
    val goals: Int = 0,
    val penalties: Int = 0,
    val attacks: Int = 0,
    val dangerous_attacks: Int = 0
)