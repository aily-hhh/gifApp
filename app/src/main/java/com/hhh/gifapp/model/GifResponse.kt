package com.hhh.gifapp.model

data class GifResponse(
    val data: List<GifData>,
    val meta: Meta?,
    val pagination: Pagination?
)