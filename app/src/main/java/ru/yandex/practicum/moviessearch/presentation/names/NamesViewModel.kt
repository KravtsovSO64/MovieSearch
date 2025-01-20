package ru.yandex.practicum.moviessearch.presentation.names

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.yandex.practicum.moviessearch.domain.api.NamesIteractor
import ru.yandex.practicum.moviessearch.domain.models.Name
import ru.yandex.practicum.moviessearch.presentation.movies.SingleLiveEvent

class NamesViewModel(private val context: Context,
    private val namesIteractor: NamesIteractor): ViewModel() {

        companion object {
            private const val SEARCH_DEBOUNCE_DELAY = 2000L
            private val SEARCH_REQUEST_TOKEN = Any()
        }

    private val handler = Handler(Looper.getMainLooper())

    private val stateLiveData = MutableLiveData<NamesState>()
    fun observeState(): LiveData<NamesState> = stateLiveData

    private val showToast = SingleLiveEvent<String?>()
    fun observeShowToast(): LiveData<String?> = showToast

    private var latestSearchText: String? = null

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        handler.removeCallbacksAndMessages(NamesViewModel.SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { searchRequest(changedText) }

        val postTime = SystemClock.uptimeMillis() + NamesViewModel.SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            NamesViewModel.SEARCH_REQUEST_TOKEN,
            postTime,
        )
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