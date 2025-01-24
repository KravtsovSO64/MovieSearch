package ru.yandex.practicum.moviessearch.ui.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yandex.practicum.moviessearch.domain.models.Movie

class MoviesAdapter(private val clickListener: MovieClickListener,
                    private val trailerClickListener: TrailerClickListener) : RecyclerView.Adapter<MovieViewHolder>() {

    var movies = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(parent,  trailerClickListener::onTrailerClick)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { clickListener.onMovieClick(movie) }
        holder.trailer.setOnClickListener { trailerClickListener.onTrailerClick(movie.id) }
    }

    override fun getItemCount(): Int = movies.size

    fun interface MovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    fun interface TrailerClickListener {
        fun onTrailerClick(movieId: String)
    }
}