package ru.yandex.practicum.moviessearch.presentation.names

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.yandex.practicum.moviessearch.domain.api.NamesIteractor
import ru.yandex.practicum.moviessearch.domain.models.Name
import ru.yandex.practicum.moviessearch.presentation.movies.SingleLiveEvent

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
    private var searchJob: Job? = null

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            searchRequest(changedText)
        }
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(NamesState.Loading)
        }

        namesIteractor.searchName(newSearchText, object : NamesIteractor.NameConsumer{
            override fun consume(foundName: List<Name>?, errorMessage: String?) {
                val names = mutableListOf<Name>()
                if (foundName != null) {
                    names.addAll(foundName)
                }

                when {
                    errorMessage != null -> {
                        renderState(
                            NamesState.Error(
                                message = "Что-то пошло не так",
                            )
                        )
                        showToast.postValue(errorMessage)
                    }

                    names.isEmpty() -> {
                        renderState(
                            NamesState.Empty(
                                message = "Ничего не найдено",
                            )
                        )
                    }

                    else -> {
                        renderState(
                            NamesState.Content(
                                names = names,
                            )
                        )
                    }
                }
            }
        })
    }

    private fun renderState(state: NamesState) {
        stateLiveData.postValue(state)
    }

}