package ru.yandex.practicum.moviessearch.data.dto

data class TrailerDto(
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val link: String,
    val linkEmbed: String,
    val thumbnailUrl: String,
    val title: String,
    val type: String,
    val uploadDate: String,
    val videoDescription: String,
    val videoId: String,
    val videoTitle: String,
    val year: String
)