package com.hythes.jsonplaceholder.application.ui.page.main

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hythes.jsonplaceholder.application.data.connection.ApiResponse
import com.hythes.jsonplaceholder.application.data.model.PostModel
import com.hythes.jsonplaceholder.application.data.model.connection.FailureModel
import com.hythes.jsonplaceholder.application.data.model.connection.StatusRequestModel
import com.hythes.jsonplaceholder.application.data.repository.AppRepository

class MainViewModel(val repository: AppRepository) {
    val posts = MutableLiveData<StatusRequestModel<ArrayList<PostModel>>>()

    fun getPosts() {
        posts.postValue(StatusRequestModel.loading())
        repository.getPosts(object : ApiResponse {
            override fun <T> onSuccess(data: T) {
                val type = object : TypeToken<ArrayList<PostModel>?>() {}.type
                val list: ArrayList<PostModel> = Gson().fromJson(data.toString(), type)

                if (list.isNullOrEmpty()) {
                    posts.postValue(StatusRequestModel.empty())
                } else {
                    posts.postValue(StatusRequestModel.success(list))
                }
            }

            override fun onError(model: FailureModel?) {
                posts.postValue(StatusRequestModel.error(model))
            }
        })
    }
}