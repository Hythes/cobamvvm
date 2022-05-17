package com.hythes.jsonplaceholder.application.data.connection

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiInterface {

    @GET("posts")
    fun getPosts() : Observable<JsonObject?>


}