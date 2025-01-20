package ru.yandex.practicum.moviessearch.domain.api

import ru.yandex.practicum.moviessearch.domain.models.Name

interface NamesIteractor {

    fun searchName(expression: String, consumer: NameConsumer)

    interface NameConsumer{
        fun consume(foundName: List<Name>?, errorMessage: String?)
    }
}