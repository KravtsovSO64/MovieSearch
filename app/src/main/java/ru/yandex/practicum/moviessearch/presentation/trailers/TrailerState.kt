package ru.yandex.practicum.moviessearch.presentation.trailers

import ru.yandex.practicum.moviessearch.domain.models.Trailer
import ru.yandex.practicum.moviessearch.presentation.names.NamesState

sealed interface TrailerState {

    data class Content(
        val trailer: Trailer
    ): TrailerState

    data class Error(
        val message: String
    ): TrailerState

    object Loading : TrailerState

}