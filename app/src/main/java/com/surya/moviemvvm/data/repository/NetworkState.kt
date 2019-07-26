package com.surya.moviemvvm.data.repository

/**
 * Created by suryamudti on 26/07/2019.
 */

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED,
    ENDOFLIST
}

class NetworkState(
    val status: Status,
    val message: String
) {
    companion object{
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS,"Success")
            LOADING = NetworkState(Status.RUNNING,"Running")
            ERROR = NetworkState(Status.FAILED,"Wow something went wrong")
            ENDOFLIST = NetworkState(Status.ENDOFLIST,"You have reach the limit")
        }
    }

}