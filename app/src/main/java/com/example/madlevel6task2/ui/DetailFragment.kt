package com.example.madlevel6task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.example.madlevel6task2.databinding.FragmentDetailBinding
import com.example.madlevel6task2.model.Movie

// A simple [Fragment] subclass as the second destination in the navigation.
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment.
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the fragment result from the MoviesFragment (which is a Movie object) and pass it onto the bind function.
        setFragmentResultListener(REQ_MOVIE_KEY) { _, bundle -> bundle.getParcelable<Movie>(BUNDLE_MOVIE_KEY)?.let { bind(it) } }
    }

    // Bind the movie information and pictures with the corresponding TextViews and ImageViews.
    private fun bind(movie: Movie) {
        context?.let { Glide.with(it).load(movie.getPosterUrl()).into(binding.ivPoster) }
        context?.let { Glide.with(it).load(movie.getBackdropUrl()).into(binding.ivBackdrop) }
        binding.tvTitle.text = movie.title
        binding.tvReleaseDate.text = movie.release_date
        binding.tvRating.text = movie.vote_average.toString()
        binding.tvOverview.text = movie.overview
    }
}