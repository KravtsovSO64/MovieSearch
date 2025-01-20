package ru.yandex.practicum.moviessearch.di

import org.koin.dsl.module
import ru.yandex.practicum.moviessearch.domain.api.MoviesInteractor
import ru.yandex.practicum.moviessearch.domain.api.NamesIteractor
import ru.yandex.practicum.moviessearch.domain.impl.MoviesInteractorImpl
import ru.yandex.practicum.moviessearch.domain.impl.NamesIteractorImpl

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }

    single<NamesIteractor>{
        NamesIteractorImpl(get())
    }

}