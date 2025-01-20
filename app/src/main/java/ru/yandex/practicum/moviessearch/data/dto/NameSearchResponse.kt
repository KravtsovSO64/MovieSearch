package ru.yandex.practicum.moviessearch.data.dto

class NameSearchResponse(val errorMessage: String,
                         val expression: String,
                         val results: List<NameDto>,
                         val searchType: String): Response() {
}