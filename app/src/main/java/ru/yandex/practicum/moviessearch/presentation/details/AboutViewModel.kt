package ru.yandex.practicum.moviessearch.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.yandex.practicum.moviessearch.domain.api.MoviesInteractor
import ru.yandex.practicum.moviessearch.domain.models.MovieDetails
import ru.yandex.practicum.moviessearch.domain.models.Trailer

class AboutViewModel(private val movieId: String,
                     private val moviesInteractor: MoviesInteractor, ) : ViewModel() {


    private val stateLiveData = MutableLiveData<AboutState>()
    fun observeState(): LiveData<AboutState> = stateLiveData

    init {
        viewModelScope.launch {
            moviesInteractor.getMoviesDetails(movieId).collect{ pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(foundMovieDetails: MovieDetails?, errorMessage: String?){
        if (foundMovieDetails != null) {
            stateLiveData.postValue(AboutState.Content(foundMovieDetails))
        } else {
            stateLiveData.postValue(AboutState.Error(errorMessage ?: "Unknown error"))
        }
    }
}

