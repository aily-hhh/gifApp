package com.hhh.gifapp.data.api

import javax.inject.Inject

class GifRepository @Inject constructor(private val gifServise: GifServise) {

    suspend fun getGifs(query: String) =
        gifServise.getGifs(query = query)
}