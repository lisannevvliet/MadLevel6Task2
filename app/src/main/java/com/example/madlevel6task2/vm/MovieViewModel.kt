package com.example.madlevel6task2.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madlevel6task2.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val movieRepository = MovieRepository()
    // Points directly to the LiveData in the movieRepository.
    val movies = movieRepository.movies
    private val _errorText: MutableLiveData<String> = MutableLiveData()

    // Expose non MutableLiveData via getter. errorText can be observed from an activity for error showing. Encapsulation.
    val errorText: LiveData<String>
    get() = _errorText

    // The viewModelScope is bound to Dispatchers.Main and will automatically be cancelled when the ViewModel is cleared.
    fun getMovies(year: Int) {
        viewModelScope.launch {
            try {
                movieRepository.getMovies(year)
            } catch (error: MovieRepository.MoviesFetchError) {
                _errorText.value = error.message
                Log.e("Movies error.", error.cause.toString())
            }
        }
    }
}