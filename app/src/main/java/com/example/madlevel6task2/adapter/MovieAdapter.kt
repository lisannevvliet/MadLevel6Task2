package com.example.madlevel6task2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madlevel6task2.R
import com.example.madlevel6task2.databinding.ItemMovieBinding
import com.example.madlevel6task2.model.Movie

class MovieAdapter(private val movies: List<Movie>, private val onClick: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init { itemView.setOnClickListener { onClick(movies[adapterPosition]) } }

        private val binding = ItemMovieBinding.bind(itemView)

        // Bind the movie ranking and poster with the corresponding TextView and ImageView.
        fun bind(movie: Movie) {
            binding.tvRanking.text = "${adapterPosition + 1}."
            Glide.with(context).load(movie.getPosterUrl()).into(binding.ivPoster)
        }
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])
}