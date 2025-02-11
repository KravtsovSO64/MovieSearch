package ru.yandex.practicum.moviessearch.presentation.names

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.yandex.practicum.moviessearch.R
import ru.yandex.practicum.moviessearch.domain.api.NamesIteractor
import ru.yandex.practicum.moviessearch.domain.models.Name
import ru.yandex.practicum.moviessearch.presentation.movies.MoviesViewModel
import ru.yandex.practicum.moviessearch.presentation.movies.MoviesViewModel.Companion
import ru.yandex.practicum.moviessearch.presentation.movies.SingleLiveEvent
import ru.yandex.practicum.moviessearch.utils.debounce

class NamesViewModel(private val context: Context,
    private val namesIteractor: NamesIteractor): ViewModel() {

        companion object {
            private const val SEARCH_DEBOUNCE_DELAY = 2000L
        }


    private val stateLiveData = MutableLiveData<NamesState>()
    fun observeState(): LiveData<NamesState> = stateLiveData

    private val showToast = SingleLiveEvent<String?>()
    fun observeShowToast(): LiveData<String?> = showToast

    private var latestSearchText: String? = null
    private val nameSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) {
            changedText -> searchRequest(changedText)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }
        nameSearchDebounce(changedText)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(NamesState.Loading)
        }

        viewModelScope.launch {
            namesIteractor.searchName(newSearchText)
                .collect {
                        pair -> processResult(pair.first, pair.second)
                }
        }
    }

    private fun renderState(state: NamesState) {
        stateLiveData.postValue(state)
    }

    private fun processResult(foundName: List<Name>?, errorMessage: String?){
        val persons = mutableListOf<Name>()
        if (foundName != null) {
            persons.addAll(foundName)
        }
        when {
            errorMessage != null -> {
                renderState(NamesState.Error(message = context.getString(R.string.something_went_wrong)))
            }
            persons.isEmpty() -> {
                renderState(NamesState.Empty(message = context.getString(R.string.nothing_found)))
            }
            else -> {
                renderState(NamesState.Content(names = persons))
            }
        }
    }

}