package com.hhh.gifapp.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hhh.gifapp.model.GifData

class GifPagingSource(
    private val gifServise: GifServise,
    private val query: String
): PagingSource<Int, GifData>() {

    override fun getRefreshKey(state: PagingState<Int, GifData>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifData> {
        val pageIndex = params.key ?: 0

        return try {
            val gifs = gifServise.getGifs(query = query, offset = pageIndex * 25).body()?.data ?: emptyList()
            val responseData = mutableListOf<GifData>()
            responseData.addAll(gifs)
            val nextKey = if (gifs.size == params.loadSize) pageIndex + (params.loadSize / 25) else null
            val prevKey = if (pageIndex == 0) null else pageIndex - 1

            LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}