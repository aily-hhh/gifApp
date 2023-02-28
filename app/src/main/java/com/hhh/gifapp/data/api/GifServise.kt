package com.hhh.gifapp.data.api

import com.hhh.gifapp.util.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface GifServise {

    @GET("/v1/gifs/search")
    suspend fun getGifs(
        @Query("q") query: String,
        @Query("api_key") apiKey: String = API_KEY
    )
}