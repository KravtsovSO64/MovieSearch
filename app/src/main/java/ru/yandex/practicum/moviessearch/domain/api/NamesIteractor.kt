package ru.yandex.practicum.moviessearch.domain.api

import kotlinx.coroutines.flow.Flow
import ru.yandex.practicum.moviessearch.domain.models.Name

interface NamesIteractor {

    fun searchName(expression: String): Flow<Pair<List<Name>?, String?>>

}