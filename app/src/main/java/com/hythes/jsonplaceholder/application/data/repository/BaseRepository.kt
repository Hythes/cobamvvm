package com.hythes.jsonplaceholder.application.data.repository

import android.content.Context
import com.hythes.jsonplaceholder.application.data.connection.ApiConfig
import com.hythes.jsonplaceholder.application.data.connection.ApiInterface
import com.hythes.jsonplaceholder.application.data.connection.ApiResponse
import com.hythes.jsonplaceholder.application.data.model.connection.FailureModel
import com.hythes.jsonplaceholder.application.util.connection.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

abstract class BaseRepository {

    val msgFailure =
        "Tidak bisa menghubungi server, cek koneksi internet anda atau cobalah sesaat lagi"
    val msgFailure2 = "Kesalahan tidak diketahui"

    val apiService: ApiInterface = ApiConfig().apiService
    val accept = "application/json"

    fun <T> requestApi(
        context: Context,
        observable: Observable<T>,
        listener: ApiResponse
    ) {
        CompositeDisposable().add(
            observable.compose(SchedulerProvider().ioToMainObservableScheduler())
                .subscribe({
                    try {
                        val json = JSONObject(it.toString())
                        val data = json.get("data")
                        listener.onSuccess(data)
                    } catch (e: Exception) {
                        val model = FailureModel(
                            messageShow = msgFailure,
                            messageSystem = e.message,
                            errorCode = 400
                        )
                        listener.onError(model)

                    }
                }, { t ->
                    when (t) {
                        is HttpException -> {
                            val errorBody = t.response()?.errorBody()?.string()
                            try {
                                val json = JSONObject(errorBody.toString())
                                val message = json.getString("message")
                                val statusCode = json.getString("code")

                                val model = FailureModel(
                                    messageShow = msgFailure,
                                    messageSystem = "HttpException cause $errorBody",
                                    errorCode = t.code()
                                )

                                listener.onError(model)
                            }catch(e : Exception){
                                val model = FailureModel(
                                    messageShow = msgFailure,
                                    messageSystem = "HttpException (Catch Body) cause ${e.message}",
                                    errorCode = t.code()
                                )
                                listener.onError(model)
                            }
                        }
                        is UnknownHostException -> {
                            val model = FailureModel(
                                messageShow = msgFailure,
                                messageSystem = t.message,
                                errorCode = 404
                            )
                            listener.onError(model)
                        }
                        else -> {
                            val model = FailureModel(
                                messageShow = msgFailure2,
                                messageSystem = t.message,
                                errorCode = 404
                            )
                            listener.onError(model)
                        }
                    }

                })
        )
    }
}