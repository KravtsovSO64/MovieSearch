package ru.yandex.practicum.moviessearch.data.dto

class MoviesSearchResponse(val expression: String,
                           val results: List<MovieDto>) : Response()