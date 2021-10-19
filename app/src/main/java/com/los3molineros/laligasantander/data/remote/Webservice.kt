package com.los3molineros.laligasantander.data.remote

import com.google.gson.GsonBuilder
import com.los3molineros.laligasantander.common.AppConstants
import com.los3molineros.laligasantander.data.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Webservice {
    @GET("seasons/")
    suspend fun getSeasonId(
        @Query("apikey") apikey: String = AppConstants.API_KEY,
        @Query("league_id") league_id: Int)
    : SeasonResponse

    @GET("standings")
    suspend fun getStandings(
        @Query("apikey") apikey: String = AppConstants.API_KEY,
        @Query("season_id") season_id: Int)
    : StandingsResponse

    @GET("teams/{team}")
    suspend fun getTeam(
        @Path("team") teamId: Int,
        @Query("apikey") apikey: String = AppConstants.API_KEY
    ): TeamResponse

    @GET("matches")
    suspend fun getMatches(
        @Query("apikey") apikey: String = AppConstants.API_KEY,
        @Query("season_id") season_id: Int
    ): MatchesResponse

    @GET("matches/{match}")
    suspend fun getMatchById(
        @Path("match") matchId: Int,
        @Query("apikey") apikey: String = AppConstants.API_KEY
    ): MatchResultResponse
}

object RetrofitClient {
    val webservice: Webservice by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(Webservice::class.java)
    }
}