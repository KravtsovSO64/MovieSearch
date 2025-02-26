package ru.yandex.practicum.moviessearch.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.yandex.practicum.moviessearch.presentation.cast.MoviesCastViewModel
import ru.yandex.practicum.moviessearch.presentation.details.AboutViewModel
import ru.yandex.practicum.moviessearch.presentation.details.PosterViewModel
import ru.yandex.practicum.moviessearch.presentation.history.HistoryViewModel
import ru.yandex.practicum.moviessearch.presentation.movies.MoviesViewModel
import ru.yandex.practicum.moviessearch.presentation.names.NamesViewModel
import ru.yandex.practicum.moviessearch.presentation.trailers.TrailersViewModel

val viewModelModule = module {

    viewModel {
        MoviesViewModel(androidContext(), get())
    }

    viewModel {(movieId: String) ->
        AboutViewModel(movieId, get())
    }

    viewModel {
        TrailersViewModel(get())
    }

    viewModel {(posterUrl: String) ->
        PosterViewModel(posterUrl)
    }

    viewModel { (movieId: String) ->
        MoviesCastViewModel(movieId, get())
    }

    viewModel {
        NamesViewModel(androidContext(), get())
    }

    viewModel {
        HistoryViewModel(androidContext(), get())
    }
}
