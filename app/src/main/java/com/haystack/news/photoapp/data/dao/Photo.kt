package com.haystack.news.photoapp.data.dao

data class Photo(
    val id: String,
    val owner: String,
    val title: String,
    val datetaken: String,
    val url_h: String?,
    val height_h: Int,
    val width_h: Int,
    val ownername: String
)
