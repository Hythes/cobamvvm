package com.hythes.jsonplaceholder.application.data.repository

import android.content.Context
import com.hythes.jsonplaceholder.application.data.connection.ApiResponse

class AppRepository(val context : Context) : BaseRepository() {

    fun getPosts(listener : ApiResponse){
        val service = apiService.getPosts()
        requestApi(context,service,listener)
    }
}