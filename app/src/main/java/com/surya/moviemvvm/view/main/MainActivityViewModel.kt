package com.surya.moviemvvm.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.surya.moviemvvm.data.model.Movie
import com.surya.moviemvvm.data.repository.MovieDetailsRepository
import com.surya.moviemvvm.data.repository.MoviePagedListRepository
import com.surya.moviemvvm.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by suryamudti on 27/07/2019.
 */
class MainActivityViewModel(repository: MoviePagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movie>> by lazy {
        repository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        repository.getNetworkState()
    }

    fun listIsEmpty() : Boolean{
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}