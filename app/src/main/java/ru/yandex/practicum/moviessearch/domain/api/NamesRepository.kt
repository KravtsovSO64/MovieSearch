package ru.yandex.practicum.moviessearch.domain.api

import ru.yandex.practicum.moviessearch.domain.models.Name
import ru.yandex.practicum.moviessearch.util.Resource

interface NamesRepository {
    fun searchName(expression: String): Resource<List<Name>>
}