package com.surya.moviemvvm.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surya.moviemvvm.R
import com.surya.moviemvvm.data.api.MovieDBInterface
import com.surya.moviemvvm.data.api.TheMovieDBClient
import com.surya.moviemvvm.data.repository.MoviePagedListRepository
import com.surya.moviemvvm.view.details.MovieDetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel : MainActivityViewModel
    lateinit var repository : MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService : MovieDBInterface = TheMovieDBClient.getClient()

        repository = MoviePagedListRepository(apiService)




    }
}
