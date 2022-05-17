package com.hythes.jsonplaceholder.application.data.connection

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    private fun setConnection() : Retrofit{
        val gson = GsonBuilder().setLenient().create()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request()

                    request.newBuilder().addHeader("Accept","application/json").build()
                    request.newBuilder().addHeader("Content-Type","application/json").build()

                    val response = chain.proceed(request)

                    return response
                }
            }).build()


        return Retrofit.Builder()
            .baseUrl(AppConfig().baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    }

    val apiService = setConnection().create(ApiInterface::class.java)
}