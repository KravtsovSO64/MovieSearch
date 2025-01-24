package ru.yandex.practicum.moviessearch.presentation.trailers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.yandex.practicum.moviessearch.domain.api.MoviesInteractor
import ru.yandex.practicum.moviessearch.domain.api.NamesIteractor
import ru.yandex.practicum.moviessearch.domain.models.Trailer

class TrailersViewModel(private val iteractor: MoviesInteractor): ViewModel() {

    private val stateLiveData = MutableLiveData<TrailerState>()
    fun observeState(): LiveData<TrailerState> = stateLiveData

    fun getTrailer(movieId: String) {
        iteractor.getTrailer(movieId, object: MoviesInteractor.TrailerConsumer{

            override fun consume(trailer: Trailer?, errorMessage: String?) {
                if (trailer != null) {
                    stateLiveData.postValue(TrailerState.Content(trailer))
                } else {
                    stateLiveData.postValue(TrailerState.Error(errorMessage ?: "Unknown error"))
                }
            }
        })
    }
}