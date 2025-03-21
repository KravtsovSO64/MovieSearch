package ru.yandex.practicum.moviessearch.presentation.movies

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.yandex.practicum.moviessearch.R
import ru.yandex.practicum.moviessearch.domain.api.MoviesInteractor
import ru.yandex.practicum.moviessearch.domain.models.Movie
import ru.yandex.practicum.moviessearch.utils.debounce

class MoviesViewModel(private val context: Context,
                      private val moviesInteractor: MoviesInteractor) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private var latestSearchText: String? = null
    private val movieSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) {
        changedText -> searchRequest(changedText)
    }

    private val stateLiveData = MutableLiveData<MoviesState>()
    fun observeState(): LiveData<MoviesState> = stateLiveData

    private val showToast = SingleLiveEvent<String?>()
    fun observeShowToast(): LiveData<String?> = showToast

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        movieSearchDebounce(changedText)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(MoviesState.Loading)
            viewModelScope.launch {
                moviesInteractor.searchMovies(newSearchText)
                    .collect{pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }

    private fun processResult(foundMovie: List<Movie>?, errorMessage: String?){
        val movies = mutableListOf<Movie>()
        if (foundMovie != null) {
            movies.addAll(foundMovie)
        }
        when {
            errorMessage != null -> {
                renderState(MoviesState.Error(message = context.getString(R.string.something_went_wrong)))
                showToast.postValue(errorMessage)
            }
            movies.isEmpty() -> {
                renderState(MoviesState.Empty(message = context.getString(R.string.nothing_found)))
            }
            else -> {
                renderState(MoviesState.Content(movies))
            }
        }
    }

    private fun renderState(state: MoviesState) {
        stateLiveData.postValue(state)
    }
}