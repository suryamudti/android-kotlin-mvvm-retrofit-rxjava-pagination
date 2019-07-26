package com.surya.moviemvvm.data.repository

import androidx.lifecycle.LiveData
import com.surya.moviemvvm.data.api.MovieDBInterface
import com.surya.moviemvvm.data.model.MovieDetails
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by suryamudti on 26/07/2019.
 */
class MovieDetailsRepository(private val apiService : MovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingle (compositeDisposable: CompositeDisposable, movie:Int) : LiveData<MovieDetails>{
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetail(movie)


        return movieDetailsNetworkDataSource.movieDetailsResponse

    }

    fun getMovieDetailNetworkState() : LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState

    }
}