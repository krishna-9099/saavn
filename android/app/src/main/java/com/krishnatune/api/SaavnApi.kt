package com.krishnatune.api

import com.krishnatune.data.home.FeaturedPlaylistResponse
import com.krishnatune.data.home.PagedHomeSectionResponse
import com.krishnatune.models.HomeDataResponse
import com.krishnatune.models.HomeSectionItem
import com.krishnatune.models.AutocompleteResponse
import com.krishnatune.models.SearchResponse
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

    @GET("api.php")
    suspend fun getFeaturedPlaylists(
        @Query("__call") call: String = "content.getFeaturedPlaylists",
        @Query("_format") format: String = "json",
        @Query("_marker") marker: String = "0",
        @Query("ctx") ctx: String = "web6dot0",
        @Query("api_version") apiVersion: String = "6",
        @Query("n") count: Int,
        @Query("p") page: Int
    ): FeaturedPlaylistResponse

    @GET("api.php")
    suspend fun getTrending(
        @Query("__call") call: String = "content.getTrending",
        @Query("_format") format: String = "json",
        @Query("_marker") marker: String = "0",
        @Query("ctx") ctx: String = "web6dot0",
        @Query("api_version") apiVersion: String = "6",
        @Query("n") count: Int
    ): List<HomeSectionItem>

    @GET("api.php")
    suspend fun getAlbums(
        @Query("__call") call: String = "content.getAlbums",
        @Query("_format") format: String = "json",
        @Query("_marker") marker: String = "0",
        @Query("ctx") ctx: String = "web6dot0",
        @Query("api_version") apiVersion: String = "6",
        @Query("n") count: Int
    ): PagedHomeSectionResponse

    @GET("api.php")
    suspend fun getCharts(
        @Query("__call") call: String = "content.getCharts",
        @Query("_format") format: String = "json",
        @Query("_marker") marker: String = "0",
        @Query("ctx") ctx: String = "web6dot0",
        @Query("api_version") apiVersion: String = "6",
        @Query("n") count: Int
    ): List<HomeSectionItem>

    @GET("api.php")
    suspend fun searchAll(
        @Query("__call") call: String = "search.getResults",
        @Query("_format") format: String = "json",
        @Query("_marker") marker: String = "0",
        @Query("ctx") ctx: String = "web6dot0",
        @Query("api_version") apiVersion: String = "6",
        @Query("q") query: String,
        @Query("n") count: Int = 24
    ): SearchResponse

    @GET("api.php")
    suspend fun getAutocomplete(
        @Query("__call") call: String = "autocomplete.get",
        @Query("_format") format: String = "json",
        @Query("_marker") marker: String = "0",
        @Query("ctx") ctx: String = "web6dot0",
        @Query("api_version") apiVersion: String = "6",
        @Query("query") query: String
    ): AutocompleteResponse

    @GET("api.php")
    suspend fun searchSongs(
        @Query("__call") call: String = "search.getResults",
        @Query("_format") format: String = "json",
        @Query("_marker") marker: String = "0",
        @Query("ctx") ctx: String = "web6dot0",
        @Query("api_version") apiVersion: String = "6",
        @Query("q") query: String,
        @Query("p") page: Int = 0,
        @Query("n") count: Int = 10
    ): SearchResponse

    @GET("api.php")
    suspend fun searchAlbums(
        @Query("__call") call: String = "search.getAlbumResults",
        @Query("_format") format: String = "json",
        @Query("_marker") marker: String = "0",
        @Query("ctx") ctx: String = "web6dot0",
        @Query("api_version") apiVersion: String = "6",
        @Query("q") query: String,
        @Query("p") page: Int = 0,
        @Query("n") count: Int = 10
    ): SearchResponse

    @GET("api.php")
    suspend fun searchArtists(
        @Query("__call") call: String = "search.getArtistResults",
        @Query("_format") format: String = "json",
        @Query("_marker") marker: String = "0",
        @Query("ctx") ctx: String = "web6dot0",
        @Query("api_version") apiVersion: String = "6",
        @Query("q") query: String,
        @Query("p") page: Int = 0,
        @Query("n") count: Int = 10
    ): SearchResponse

    @GET("api.php")
    suspend fun searchPlaylists(
        @Query("__call") call: String = "search.getPlaylistResults",
        @Query("_format") format: String = "json",
        @Query("_marker") marker: String = "0",
        @Query("ctx") ctx: String = "web6dot0",
        @Query("api_version") apiVersion: String = "6",
        @Query("q") query: String,
        @Query("p") page: Int = 0,
        @Query("n") count: Int = 10
    ): SearchResponse

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
