package com.los3molineros.laligasantander.data.model

import java.util.*


data class Params(
    val lastSeasonCheck: Date? = null,
    val lastStandingCheck: Date? = null,
    val lastMatchCheck: Date? = null,
    val leagueId: Int = 0,
    var seasonId: Int? = null,
    val matchesCheckTime: Int = 0,
    val seasonCheckTime: Int = 0,
    val standingsCheckTime: Int = 0,
)