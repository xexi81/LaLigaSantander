package com.los3molineros.laligasantander.data.model

data class MatchesResponse(
    val query: Query = Query(),
    val data: List<Match> = listOf()
)

data class MatchesResponseFirestore(
    val query: Query = Query(),
    val data: List<MatchFirestore> = listOf()
)

data class Match(
    val match_id: Int = 0,
    val status_code: Int = 0,
    val status: String = "",
    val match_start: String = "",
    val match_start_iso: String = "",
    val minute: Int = 0,
    val league_id: Int = 0,
    val season_id: Int = 0,
    val stage: Stage = Stage(),
    val group: Group = Group(),
    val round: Round = Round(),
    val home_team: Team = Team(),
    val away_team: Team = Team(),
    val stats: Stats = Stats(),
    val venue: Venue = Venue(),
)

data class MatchFirestore(
    val match_id: Int = 0,
    val status_code: Int = 0,
    val status: String = "",
    val match_start: String = "",
    val match_start_iso: String = "",
    val minute: Int = 0,
    val league_id: Int = 0,
    val season_id: Int = 0,
    val stage: Stage = Stage(),
    val group: Group = Group(),
    val round: RoundFirestore = RoundFirestore(),
    val home_team: Team = Team(),
    val away_team: Team = Team(),
    val stats: Stats = Stats(),
    val venue: Venue = Venue(),
)


data class Stage(
    val stage_id: Int = 0,
    val name: String = ""
)

data class Group(
    val group_id: Int = 0,
    val group_name: String = ""
)

data class Round(
    val round_id: Int = 0,
    val name: String = "",
    val is_current: Int? = null
)

data class RoundFirestore(
    val round_id: Int = 0,
    val name: String = "",
    val _current: Int? = null
)


data class Stats(
    val home_score: Int = 0,
    val away_score: Int = 0,
    val ht_score: String? = null,
    val ft_score: String? = null,
    val et_score: String? = null,
    val ps_score: String? = null,
)

data class Venue(
    val venue_id: Int = 0,
    val name: String = "",
    val capacity: Int = 0,
    val city: String = "",
    val country_id: Int = 0
)