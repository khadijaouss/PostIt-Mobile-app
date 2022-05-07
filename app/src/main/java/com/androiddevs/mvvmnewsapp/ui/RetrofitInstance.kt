package com.androiddevs.mvvmnewsapp.ui

import com.androiddevs.mvvmnewsapp.api.PostServise
import com.androiddevs.mvvmnewsapp.util.global.Companion.WEB_SERVICE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
   //Méthode permettante de consommer le service web (via retrofit et Gson)
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create((PostServise::class.java))
        }
    }
}