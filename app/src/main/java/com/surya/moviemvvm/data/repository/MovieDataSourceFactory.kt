package com.surya.moviemvvm.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.surya.moviemvvm.data.api.MovieDBInterface
import com.surya.moviemvvm.data.model.Movie
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by suryamudti on 27/07/2019.
 */
class MovieDataSourceFactory(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable
): DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }


}