package ru.yandex.practicum.moviessearch.data.db.entity

import android.icu.text.CaseMap.Title
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val resultType: String,
    val image: String,
    val title: String,
    val description: String
)