package ru.yandex.practicum.moviessearch.presentation.trailers

import ru.yandex.practicum.moviessearch.domain.models.Trailer

sealed interface TrailerState {

    data class Content(
        val trailer: Trailer
    ): TrailerState

    data class Error(
        val message: String
    ): TrailerState

}