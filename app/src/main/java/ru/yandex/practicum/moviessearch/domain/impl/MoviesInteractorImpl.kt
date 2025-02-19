package ru.yandex.practicum.moviessearch.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.yandex.practicum.moviessearch.domain.api.MoviesInteractor
import ru.yandex.practicum.moviessearch.domain.api.MoviesRepository
import ru.yandex.practicum.moviessearch.domain.models.Movie
import ru.yandex.practicum.moviessearch.domain.models.MovieCast
import ru.yandex.practicum.moviessearch.domain.models.MovieDetails
import ru.yandex.practicum.moviessearch.domain.models.Trailer
import ru.yandex.practicum.moviessearch.utils.Resource
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    override fun searchMovies(expression: String): Flow<Pair<List<Movie>?,String?>> {
        return repository.searchMovies(expression).map { result ->
            when(result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }

    override fun getMoviesDetails(movieId: String): Flow<Pair<MovieDetails?, String?>> {
        return repository.getMovieDetails(movieId).map { result ->
            when(result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }

    override fun getMovieCast(movieId: String): Flow<Pair<MovieCast?, String?>> {
        return repository.getMovieCast(movieId).map { result ->
            when(result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }

    override fun getTrailer(movieId: String): Flow<Pair<Trailer?, String?>> {
        return repository.getTrailer(movieId).map { result ->
            when(result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }
}
