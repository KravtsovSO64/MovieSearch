package ru.yandex.practicum.moviessearch.domain.api

import kotlinx.coroutines.flow.Flow
import ru.yandex.practicum.moviessearch.domain.models.Movie

interface HistoryInteractor {

    fun historyMovies(): Flow<List<Movie>>

}