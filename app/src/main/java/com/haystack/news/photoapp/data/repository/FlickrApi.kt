package com.haystack.news.photoapp.data.repository

import com.haystack.news.photoapp.data.dao.FlickrResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    @GET("?method=flickr.interestingness.getList")
    fun getData(@Query("per_page") perPage: Int, @Query("page") page: Int): Call<FlickrResponse>

    @GET("?method=flickr.photos.search")
    fun search(@Query("per_page") perPage: Int, @Query("page") page: Int, @Query("text") text: String): Call<FlickrResponse>

}