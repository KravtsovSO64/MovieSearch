package ru.yandex.practicum.moviessearch.domain.api

import kotlinx.coroutines.flow.Flow
import ru.yandex.practicum.moviessearch.domain.models.Movie
import ru.yandex.practicum.moviessearch.domain.models.MovieCast
import ru.yandex.practicum.moviessearch.domain.models.MovieDetails
import ru.yandex.practicum.moviessearch.domain.models.Trailer

interface MoviesInteractor {
    fun searchMovies(expression: String): Flow<Pair<List<Movie>?,String?>>
    fun getMoviesDetails(movieId: String): Flow<Pair<MovieDetails?, String?>>
    fun getMovieCast(movieId: String): Flow<Pair<MovieCast?, String?>>
    fun getTrailer(movieId: String): Flow<Pair<Trailer?,String?>>

    /*
    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

    interface MovieDetailsConsumer {
        fun consume(movieDetails: MovieDetails?, errorMessage: String?)
    }

    interface MovieCastConsumer {
        fun consume(movieCast: MovieCast?, errorMessage: String?)
    }

    interface TrailerConsumer {
        fun consume(trailer: Trailer?, errorMessage: String?)
    }

     */
}
