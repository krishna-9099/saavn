package com.krishnatune.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface SaavnApi {
    @GET("search")
    suspend fun searchSongs(
        @Query("query") query: String
    ): Any
}