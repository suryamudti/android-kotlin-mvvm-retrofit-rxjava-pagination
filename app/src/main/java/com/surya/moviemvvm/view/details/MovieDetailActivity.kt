package com.surya.moviemvvm.view.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.surya.moviemvvm.R
import com.surya.moviemvvm.data.api.MovieDBInterface
import com.surya.moviemvvm.data.api.TheMovieDBClient
import com.surya.moviemvvm.data.model.MovieDetails
import com.surya.moviemvvm.data.repository.MovieDetailsRepository
import com.surya.moviemvvm.data.repository.NetworkState
import com.surya.moviemvvm.util.Constant
import kotlinx.android.synthetic.main.activity_movie_detail.*
import java.text.NumberFormat
import java.util.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var repository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId: Int = intent.getIntExtra("id",1)
        val apiService: MovieDBInterface = TheMovieDBClient.getClient()

        repository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    private fun getViewModel(movieId : Int): MovieDetailViewModel{
        return ViewModelProviders.of(this,object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieDetailViewModel(repository,movieId) as T
            }
        })[MovieDetailViewModel::class.java]
    }

    fun bindUI(it: MovieDetails){
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() +" minutes"
        movie_overview.text = it.overview

        val formatCurrency: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterUrl = Constant.POSTER_BASE_URL+it.posterPath
        Glide.with(this)
            .load(moviePosterUrl)
            .into(iv_movie_poster)

    }
}
