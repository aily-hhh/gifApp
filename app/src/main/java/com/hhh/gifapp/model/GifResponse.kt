package com.hhh.gifapp.model

data class GifResponse(
    val `data`: List<Any>,
    val meta: Meta,
    val pagination: Pagination
)