package com.los3molineros.laligasantander.data.model

import com.google.gson.annotations.SerializedName

data class SeasonResponse (
    val query: Query = Query(),
    @SerializedName("data")
    val seasonList: List<Seasons> = listOf()
)


data class Query(
    var apikey: String = "",
    val league_id: String = ""
)


data class Seasons(
    val season_id: Int = 0,
    val name: String = "",
    val is_current: Int = 0,
    val country_id: Int  = 0,
    val league_id: Int = 0,
    val start_date: String = "",
    val end_date: String = ""
)

