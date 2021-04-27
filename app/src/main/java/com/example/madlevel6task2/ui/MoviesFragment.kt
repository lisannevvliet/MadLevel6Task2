package com.example.madlevel6task2.ui

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel6task2.R
import com.example.madlevel6task2.adapter.MovieAdapter
import com.example.madlevel6task2.databinding.FragmentMoviesBinding
import com.example.madlevel6task2.model.Movie
import com.example.madlevel6task2.vm.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import kotlin.math.floor

const val BUNDLE_MOVIE_KEY = "bundle_movie_key"
const val REQ_MOVIE_KEY = "req_movie_key"

// A simple [Fragment] subclass as the default destination in the navigation.
class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()
    private val movies = arrayListOf<Movie>()
    private val movieAdapter = MovieAdapter(movies, ::onClick)

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

        // Hide the progress bar on default.
        binding.progressBar.visibility = View.INVISIBLE

        // Set the layout manager and adapter of the RecyclerView.
        val gridLayoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        binding.rvMovies.layoutManager = gridLayoutManager
        binding.rvMovies.adapter = movieAdapter

        // Add a Global Layout Listener to calculate the span count.
        /*binding.rvMovies.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.rvMovies.viewTreeObserver.removeOnGlobalLayoutListener(this)
                gridLayoutManager.spanCount = calculateSpanCount()
                gridLayoutManager.requestLayout()
            }
        })*/

        // Perform a search when the enter button is clicked.
        binding.etYear.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                onSubmit()
                return@OnKeyListener true
            }
            false
        })

        // Perform a search when the submit button is clicked.
        binding.btnSubmit.setOnClickListener {
            onSubmit()
        }

        observeMovies()
    }

    /**
     * Calculate the number of spans for the recycler view based on the recycler view width.
     * @return int number of spans.
     */
    /*private fun calculateSpanCount(): Int {
        val viewWidth = binding.rvMovies.measuredWidth
        val cardViewWidth = resources.getDimension(R.dimen.poster_width)
        val cardViewMargin = resources.getDimension(R.dimen.margin_medium)
        val spanCount = floor((viewWidth / (cardViewWidth + cardViewMargin)).toDouble()).toInt()
        return if (spanCount >= 1) spanCount else 1
    }*/

    // Add the movies to the RecyclerView.
    private fun observeMovies() {
        viewModel.movies.observe(viewLifecycleOwner, {
            movies.clear()
            movies.addAll(it)
            movieAdapter.notifyDataSetChanged()
        })

        // Show an error message if the movies can't be fetched.
        viewModel.errorText.observe(viewLifecycleOwner, {
            val handler = Handler()
            handler.postDelayed({ Snackbar.make(binding.rvMovies, it, Snackbar.LENGTH_LONG).show() }, 1000)
        })
    }

    // Check if the year is filled in and valid and retrieve the results from the MovieViewModel.
    private fun onSubmit() {
        val year = binding.etYear.text
        val yearInt = year.toString().toInt()

        if (year.isNullOrBlank()) {
            Snackbar.make(binding.rvMovies, "Please fill in a year.", Snackbar.LENGTH_LONG).show()
        } else if (yearInt < 1000) {
            Snackbar.make(binding.rvMovies, "Please fill in a valid year.", Snackbar.LENGTH_LONG).show()
        } else {
            // Show the progress bar for 1 second while the movies are loading.
            binding.progressBar.visibility = View.VISIBLE
            viewModel.getMovies(yearInt)
            val handler = Handler()
            handler.postDelayed({ binding.progressBar.visibility = View.INVISIBLE }, 1000)
        }
    }

    // If a movie is clicked, set the corresponding Movie object as the fragment result and navigate to the DetailFragment.
    private fun onClick(movie: Movie) {
        setFragmentResult(REQ_MOVIE_KEY, bundleOf(Pair(BUNDLE_MOVIE_KEY, movie)))
        findNavController().navigate(R.id.action_MoviesFragment_to_DetailFragment)
    }
}