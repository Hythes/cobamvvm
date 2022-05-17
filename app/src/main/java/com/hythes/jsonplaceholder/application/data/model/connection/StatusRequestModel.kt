package com.hythes.jsonplaceholder.application.data.model.connection

import com.hythes.jsonplaceholder.application.util.connection.StatusRequest

data class StatusRequestModel<out T>(
    val statusRequest: StatusRequest,
    val data: T?,
    val failureModel: FailureModel?
) {
    companion object {
        fun <T> loading(): StatusRequestModel<T> {
            return StatusRequestModel(StatusRequest.LOADING, null, null)
        }

        fun <T> empty(): StatusRequestModel<T> {
            return StatusRequestModel(StatusRequest.EMPTY, null, null)
        }

        fun <T> success(data : T): StatusRequestModel<T> {
            return StatusRequestModel(StatusRequest.SUCCESS, data, null)
        }

        fun <T> error(failureModel: FailureModel?): StatusRequestModel<T> {
            return StatusRequestModel(StatusRequest.ERROR, null, failureModel)
        }


    }
}
