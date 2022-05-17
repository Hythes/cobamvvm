package com.hythes.jsonplaceholder.application.data.connection

import com.hythes.jsonplaceholder.application.data.model.connection.FailureModel

interface ApiResponse {
    fun <T> onSuccess(data : T)
    fun onError(model : FailureModel?)

}