package com.example.madlevel6task2.repository

import com.example.madlevel6task2.api.MovieApi
import com.example.madlevel6task2.api.MovieApiService
import com.example.madlevel6task2.model.Movie

class MovieRepository {
    private val movieApiService: MovieApiService = MovieApi.createApi()

    suspend fun getMovies(): Movie {
        return movieApiService.getMovies()
    }
}