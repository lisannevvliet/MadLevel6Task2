package com.example.madlevel6task2.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApi {
    companion object {
        // The base URL off the API.
        private const val baseUrl = "https://api.themoviedb.org/"

        /**
         * @return [MovieApiService] The service class off the Retrofit client.
         */
        fun createApi(): MovieApiService {
            // Create an OkHttpClient to be able to make a log of the network traffic.
            val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()

            // Create the Retrofit instance.
            val movieApi = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()

            // Return the Retrofit MovieApiService.
            return movieApi.create(MovieApiService::class.java)
        }
    }
}