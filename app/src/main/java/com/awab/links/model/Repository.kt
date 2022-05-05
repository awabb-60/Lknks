package com.awab.links.model

import com.awab.links.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Repository {

    val gson = GsonBuilder()
        .setLenient()
        .create()

    val client = OkHttpClient.Builder().apply {
        connectTimeout(10, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logging)
        }
    }.build()

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl("https://is.gd")
        .client(client)
        .build()

    val api = retrofit
        .create(Is_gdLinkShortenerApi::class.java)

    suspend fun shortenLink(link: String): String {

        try {
            val response = api.getShortLink(link=link)
            if (response.isSuccessful && response.body()?.shortLink != null){
                return response.body()!!.shortLink!!
            }
            throw Exception("failed to get short url")
        } catch (e: Exception) {
            e.printStackTrace()
            return "error"
        }
    }
}
