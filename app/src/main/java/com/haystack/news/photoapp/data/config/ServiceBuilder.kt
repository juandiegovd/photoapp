package com.haystack.news.photoapp.data.config

import com.google.gson.GsonBuilder
import com.haystack.news.photoapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL = "https://www.flickr.com/services/rest/"
    private const val API_KEY = BuildConfig.API_KEY
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val queryInterceptor = Interceptor { chain ->
        val request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter(
                "extras",
                "date_taken,url_h,owner_name"
            )
            .addQueryParameter("format", "json")
            .addQueryParameter("nojsoncallback", "1")
            .addQueryParameter("api_key", API_KEY)
            .build()
        val newRequest = request.newBuilder().url(url).build()

        chain.proceed(newRequest)

    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(queryInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}