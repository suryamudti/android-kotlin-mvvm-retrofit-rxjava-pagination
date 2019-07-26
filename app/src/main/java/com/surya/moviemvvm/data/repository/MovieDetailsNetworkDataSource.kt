package com.surya.moviemvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.surya.moviemvvm.data.api.MovieDBInterface
import com.surya.moviemvvm.data.model.MovieDetails
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 * Created by suryamudti on 26/07/2019.
 */
class MovieDetailsNetworkDataSource(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable)
{
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState : LiveData<NetworkState>
        get() = _networkState

    private val _movieDetailsResponse = MutableLiveData<MovieDetails>()
    val movieDetailsResponse : LiveData<MovieDetails>
        get() = _movieDetailsResponse


    fun fetchMovieDetail(movieId : Int){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _movieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            it.printStackTrace()
                            Log.e("data source", it.message)
                        }
                    )
            )
        }catch (e: Exception){
            Log.e("Data source error",e.message)
        }
    }

}