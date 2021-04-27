package com.example.madlevel6task2.api

import com.example.madlevel6task2.model.Results
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    // Retrieve the most popular movies from The Movie Database API.
    @GET("/3/discover/movie?api_key=3f0520cb0879da1e6e761032da1394d0&language=en-US&sort_by=popularity.desc&include_adult=false&page=1")
    // Add the release year parameter to the URL and return the results in a data class.
    suspend fun getMovies(@Query("year") year: Int): Results
}