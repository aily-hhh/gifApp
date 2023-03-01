package com.hhh.gifapp.model

import com.google.gson.annotations.SerializedName

data class GifData(
    @SerializedName("images")
    val images: ImageData
)

data class ImageData(
    @SerializedName("original")
    val original: OriginalData
)

data class OriginalData(
    @SerializedName("url")
    val url: String
)