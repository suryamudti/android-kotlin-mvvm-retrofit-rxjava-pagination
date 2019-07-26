package com.surya.moviemvvm.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.surya.moviemvvm.data.api.MovieDBInterface
import com.surya.moviemvvm.data.model.Movie
import com.surya.moviemvvm.util.Constant.Companion.POST_PER_PAGE
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by suryamudti on 27/07/2019.
 */
class MoviePagedListRepository(
    private val apiService: MovieDBInterface
) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>>{
        movieDataSourceFactory = MovieDataSourceFactory(apiService,compositeDisposable)

        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource,NetworkState>(
            movieDataSourceFactory.moviesLiveDataSource,MovieDataSource::networkState
        )
    }
}