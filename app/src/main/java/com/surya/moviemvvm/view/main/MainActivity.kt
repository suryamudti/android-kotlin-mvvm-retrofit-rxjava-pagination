package com.surya.moviemvvm.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.surya.moviemvvm.R
import com.surya.moviemvvm.data.api.MovieDBInterface
import com.surya.moviemvvm.data.api.TheMovieDBClient
import com.surya.moviemvvm.data.repository.MoviePagedListRepository
import com.surya.moviemvvm.data.repository.NetworkState
import com.surya.moviemvvm.view.details.MovieDetailActivity
import com.surya.moviemvvm.view.details.MovieDetailViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel : MainActivityViewModel
    lateinit var repository : MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService : MovieDBInterface = TheMovieDBClient.getClient()

        repository = MoviePagedListRepository(apiService)

        viewmodel = getViewModel()

        val movieAdapter = PopularMoviePagedListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this,3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType:Int = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }
        }

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewmodel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewmodel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if(viewmodel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if(viewmodel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            if (!viewmodel.listIsEmpty()){
                movieAdapter.setNetworkState(it)
            }

        })
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this,object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainActivityViewModel(repository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}
