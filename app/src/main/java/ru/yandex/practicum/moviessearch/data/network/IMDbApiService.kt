package ru.yandex.practicum.moviessearch.data.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import ru.yandex.practicum.moviessearch.data.dto.MovieCastResponse
import ru.yandex.practicum.moviessearch.data.dto.MovieDetailsResponse
import ru.yandex.practicum.moviessearch.data.dto.MoviesSearchResponse
import ru.yandex.practicum.moviessearch.data.dto.NameSearchResponse
import ru.yandex.practicum.moviessearch.data.dto.TrailerResponse

interface IMDbApiService {

    //Теперь наши функции для запроса в сеть с модификатором suspend
    //, что означает что они могут быть вызваны только из CoroutineScope.
    //Возвращают теперь они не Call<>, а сам объект класса реализующий interface Response.


    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    suspend fun searchMovies(@Path("expression") expression: String): MoviesSearchResponse

    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: String): MovieDetailsResponse

    @GET("/en/API/FullCast/k_zcuw1ytf/{movie_id}")
    suspend fun getFullCast(@Path("movie_id") movieId: String): MovieCastResponse

    @GET("en/API/SearchName/k_zcuw1ytf/{expression}")
    suspend fun searchName(@Path("expression") expression: String): NameSearchResponse

    @GET("en/API/Trailer/k_zcuw1ytf/{movie_id}")
    suspend fun getTrailer(@Path("movie_id") movieId: String): TrailerResponse

}