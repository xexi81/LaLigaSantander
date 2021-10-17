package com.los3molineros.laligasantander.data.model

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("query")
    val teamQuery: TeamQuery = TeamQuery(),
    @SerializedName("data")
    val team: Team = Team()
)

data class TeamQuery(
    var apikey: String = ""
)

data class Team(
    val team_id: Int = 0,
    val name: String = "",
    val short_code: String = "",
    val common_name: String = "",
    val logo: String = "",
    val country: Country = Country()
)

data class Country(
    val country_id: Int = 0,
    val name: String = "",
    val country_code: String = "",
    val continent: String = ""
)

