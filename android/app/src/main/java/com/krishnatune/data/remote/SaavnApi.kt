package com.krishnatune.data.remote

import com.krishnatune.domain.model.HomeDataResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SaavnApi {
    @GET("api.php")
    suspend fun getLaunchData(
        @Query("__call") call: String = "webapi.getLaunchData",
        @Query("_format") format: String = "json",
        @Query("_marker") marker: String = "0",
        @Query("ctx") ctx: String = "web6dot0",
        @Query("api_version") apiVersion: String = "4"
    ): HomeDataResponse

    companion object {
        private const val BASE_URL = "https://www.jiosaavn.com/"

        fun create(): SaavnApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SaavnApi::class.java)
        }
    }
}