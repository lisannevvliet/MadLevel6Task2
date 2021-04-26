package com.example.madlevel6task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.madlevel6task2.R
import com.example.madlevel6task2.adapter.MovieAdapter
import com.example.madlevel6task2.databinding.FragmentMoviesBinding
import com.example.madlevel6task2.model.Movie
import com.example.madlevel6task2.vm.MovieViewModel

// A simple [Fragment] subclass as the default destination in the navigation.
class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    private val movies = arrayListOf<Movie>()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment.
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the layout manager and adapter of the RecyclerView.
        movieAdapter = MovieAdapter(movies, ::onClick)
        binding.rvMovies.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvMovies.adapter = MovieAdapter(movies, ::onClick)

        binding.btnSubmit.setOnClickListener {
            observeMovies()
        }
    }

    private fun observeMovies() {
        /*movies.clear()
        movies.addAll(viewModel.getMovies())
        movieAdapter.notifyDataSetChanged()*/
    }

    private fun onClick(movie: Movie) {
        val arguments = Bundle()
        arguments.putString(title, movie.title)

        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, arguments)
    }
}