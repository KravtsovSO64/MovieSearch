package ru.yandex.practicum.moviessearch.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.yandex.practicum.moviessearch.domain.api.NamesIteractor
import ru.yandex.practicum.moviessearch.domain.api.NamesRepository
import ru.yandex.practicum.moviessearch.domain.models.Name
import ru.yandex.practicum.moviessearch.utils.Resource

class NamesIteractorImpl(private val repository: NamesRepository): NamesIteractor {

    override fun searchName(expression: String): Flow<Pair<List<Name>?, String?>> {
        return repository.searchName(expression).map { result ->
            when(result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }
}