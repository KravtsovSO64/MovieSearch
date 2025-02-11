package ru.yandex.practicum.moviessearch.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yandex.practicum.moviessearch.data.dto.NameSearchRequest
import ru.yandex.practicum.moviessearch.data.dto.NameSearchResponse
import ru.yandex.practicum.moviessearch.domain.api.NamesRepository
import ru.yandex.practicum.moviessearch.domain.models.Name
import ru.yandex.practicum.moviessearch.utils.Resource

class NamesRepositoryImpl(private val networkClient: NetworkClient): NamesRepository {

    override fun searchName(expression: String): Flow<Resource<List<Name>>> = flow {
        val response = networkClient.doRequest(NameSearchRequest(expression))
         when(response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }
            200 -> {
                with(response as NameSearchResponse) {
                    emit(Resource.Success(results.map {
                        Name(it.description, it.id, it.image, it.resultType, it.title )
                    }))
                }
            }
            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }
}