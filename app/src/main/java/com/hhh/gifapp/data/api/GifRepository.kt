package com.hhh.gifapp.data.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import javax.inject.Inject

class GifRepository @Inject constructor(private val gifServise: GifServise) {

    suspend fun getGifs(query: String) =
        gifServise.getGifs(query = query, offset = 0)

    fun getPagingGifs(query: String) = Pager(
        config = PagingConfig(pageSize = 25),
        pagingSourceFactory = {
            GifPagingSource(gifServise, query = query)
        }
    ).flow
}