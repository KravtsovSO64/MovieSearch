package ru.yandex.practicum.moviessearch.presentation.trailers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.yandex.practicum.moviessearch.domain.api.MoviesInteractor
import ru.yandex.practicum.moviessearch.domain.api.NamesIteractor
import ru.yandex.practicum.moviessearch.domain.models.Trailer

class TrailersViewModel(private val iteractor: MoviesInteractor): ViewModel() {

    private val stateLiveData = MutableLiveData<TrailerState>()
    fun observeState(): LiveData<TrailerState> = stateLiveData

    fun getTrailer(movieId: String) {
        viewModelScope.launch {
            iteractor.getTrailer(movieId).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(foundTrailer: Trailer?, errorMessage: String?){
        if (foundTrailer != null) {
            stateLiveData.postValue(TrailerState.Content(foundTrailer))
        } else {
            stateLiveData.postValue(TrailerState.Error(errorMessage ?: "Unknown error"))
        }
    }
}