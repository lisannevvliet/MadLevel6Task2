package com.example.madlevel6task2.vm

import androidx.lifecycle.ViewModel
import com.example.madlevel6task2.model.Movie
import com.example.madlevel6task2.repository.MovieRepository

class MovieViewModel : ViewModel() {
    private val movieRepository = MovieRepository()

    suspend fun getMovies(): Movie {
        return movieRepository.getMovies()
    }
}