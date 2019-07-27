package com.surya.moviemvvm.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.surya.moviemvvm.data.api.MovieDBInterface
import com.surya.moviemvvm.data.model.Movie
import com.surya.moviemvvm.util.Constant
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by suryamudti on 27/07/2019.
 */
class MovieDataSource(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable
    ):PageKeyedDataSource<Int, Movie>() {

    private var page = Constant.FIRST_PAGE

    val networkState : MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopular(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList,null,page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieData", it.message)
                    }
            )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopular(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key){
                            callback.onResult(it.movieList,params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }else{
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieData", it.message)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}