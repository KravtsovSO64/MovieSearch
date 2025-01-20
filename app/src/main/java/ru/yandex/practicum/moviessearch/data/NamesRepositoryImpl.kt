package ru.yandex.practicum.moviessearch.data

import ru.yandex.practicum.moviessearch.data.dto.NameSearchRequest
import ru.yandex.practicum.moviessearch.data.dto.NameSearchResponse
import ru.yandex.practicum.moviessearch.domain.api.NamesRepository
import ru.yandex.practicum.moviessearch.domain.models.Name
import ru.yandex.practicum.moviessearch.util.Resource

class NamesRepositoryImpl(private val networkClient: NetworkClient): NamesRepository {

    override fun searchName(expression: String): Resource<List<Name>> {
        val response = networkClient.doRequest(NameSearchRequest(expression))
        return when(response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                with(response as NameSearchResponse) {
                    Resource.Success(results.map {
                        Name(description = it.description, id = it.id, image = it.image, resultType = it.resultType, title= it.title )
                    })
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}