package com.example.madlevel6task2.api

import com.example.madlevel6task2.model.Movie
import retrofit2.http.GET

interface MovieApiService {
    // The GET method needed to retrieve the most popular movies of a certain year.
    @GET("/3/discover/movie?api_key=3f0520cb0879da1e6e761032da1394d0&language=en-US&sort_by=popularity.desc&include_adult=false&page=1&primary_release_year=2020")
    suspend fun getMovies(): Movie
}