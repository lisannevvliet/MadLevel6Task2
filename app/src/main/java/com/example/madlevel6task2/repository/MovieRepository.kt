package com.example.madlevel6task2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel6task2.api.MovieApi
import com.example.madlevel6task2.api.MovieApiService
import com.example.madlevel6task2.model.Movie
import kotlinx.coroutines.withTimeout

class MovieRepository {
    private val movieApiService: MovieApiService = MovieApi.createApi()
    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()

    // Expose non MutableLiveData via getter. Encapsulation.
    val movies: LiveData<List<Movie>>
    get() = _movies

    // Suspend function that calls a suspend function from the MovieApi call.
    suspend fun getMovies(year: Int) {
        try {
            // Timeout the request after 5 seconds.
            val result = withTimeout(5000) {
                movieApiService.getMovies(year)
            }

            _movies.value = result.results
        } catch (error: Throwable) {
            throw MoviesFetchError("Unable to fetch movies.", error)
        }
    }

    class MoviesFetchError(message: String, cause: Throwable) : Throwable(message, cause)
}