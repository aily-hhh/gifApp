package com.hhh.gifapp.data.api

import com.hhh.gifapp.model.GifResponse
import com.hhh.gifapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GifServise {

    @GET("v1/gifs/search")
    suspend fun getGifs(
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("limit") limit: Int = 25
    ): Response<GifResponse>
}