package ru.yandex.practicum.moviessearch.data.converters

import ru.yandex.practicum.moviessearch.data.dto.MovieDetailsResponse
import ru.yandex.practicum.moviessearch.domain.models.MovieDetails

class MovieDetailsConverter {

    fun convert(responce: MovieDetailsResponse): MovieDetails{
        return with(responce) {
            MovieDetails(
                id, title, imDbRating ?: "", year,
                countries, genres, directors, writers, stars, plot
            )
        }
    }
}