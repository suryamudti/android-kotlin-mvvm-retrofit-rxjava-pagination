package com.surya.moviemvvm.data.api

import com.surya.moviemvvm.data.model.MovieDetails
import com.surya.moviemvvm.data.model.MovieResponse
import com.surya.moviemvvm.view.details.MovieDetailActivity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by suryamudti on 26/07/2019.
 */
interface MovieDBInterface {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id:Int): Single<MovieDetails>

    @GET("movie/popular")
    fun getPopular(@Query("page")page: Int):Single<MovieResponse>



}