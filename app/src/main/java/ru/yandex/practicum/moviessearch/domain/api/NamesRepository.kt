package ru.yandex.practicum.moviessearch.domain.api

import kotlinx.coroutines.flow.Flow
import ru.yandex.practicum.moviessearch.domain.models.Name
import ru.yandex.practicum.moviessearch.utils.Resource

interface NamesRepository {
    fun searchName(expression: String):  Flow<Resource<List<Name>>>
}