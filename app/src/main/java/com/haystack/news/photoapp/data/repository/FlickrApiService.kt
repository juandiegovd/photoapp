package com.haystack.news.photoapp.data.repository

import android.util.Log
import com.haystack.news.photoapp.data.config.ServiceBuilder
import com.haystack.news.photoapp.data.dao.FlickrResponse
import com.haystack.news.photoapp.data.dao.PhotoPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlickrApiService {
    fun fetchPhotos(page: Int, pageNumber: Int, onResult: (PhotoPage) -> Unit){
        val api: FlickrApi = ServiceBuilder.buildService(FlickrApi::class.java)

        val call = api.getData(page=page, perPage = pageNumber)
        call.enqueue(object: Callback<FlickrResponse>{
            override fun onResponse(call: Call<FlickrResponse>, response: Response<FlickrResponse>) {
                if (response.isSuccessful) {
                    val photos = response.body()?.photos
                    onResult(photos!!)
                }
            }

            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Log.d("FlickrApiService Error: ", ""+t.message)
            }
        })
    }

    fun searchPhotos(page: Int, pageNumber: Int, search: String, onResult: (PhotoPage) -> Unit){
        val api: FlickrApi = ServiceBuilder.buildService(FlickrApi::class.java)

        val call = api.search(page=page, perPage = pageNumber, text = search)
        call.enqueue(object: Callback<FlickrResponse>{
            override fun onResponse(call: Call<FlickrResponse>, response: Response<FlickrResponse>) {
                if (response.isSuccessful) {
                    val photos = response.body()?.photos
                    onResult(photos!!)
                }
            }

            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Log.d("FlickrApiService Error: ", ""+t.message)
            }
        })
    }
}