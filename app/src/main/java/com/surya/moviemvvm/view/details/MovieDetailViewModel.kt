package com.surya.moviemvvm.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.surya.moviemvvm.data.model.MovieDetails
import com.surya.moviemvvm.data.repository.MovieDetailsRepository
import com.surya.moviemvvm.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by suryamudti on 26/07/2019.
 */
class MovieDetailViewModel(
    private val movieRepository: MovieDetailsRepository,
    private val movie_id: Int
) :ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingle(compositeDisposable,movie_id)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}