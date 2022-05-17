package com.hythes.jsonplaceholder.application.ui.page.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.hythes.jsonplaceholder.R
import com.hythes.jsonplaceholder.application.data.repository.AppRepository
import com.hythes.jsonplaceholder.application.ui.base.BaseActivity
import com.hythes.jsonplaceholder.application.ui.dialog.DialogMessage
import com.hythes.jsonplaceholder.application.util.StatusView
import com.hythes.jsonplaceholder.application.util.connection.StatusRequest

class MainActivity : BaseActivity() {

    lateinit var mainViewModel: MainViewModel
    var exitApplication: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = MainViewModel(AppRepository(this))
        setupObserver()

        mainViewModel.getPosts()
    }

    private fun setupObserver() {
        mainViewModel.posts.observe(this, Observer {
            when (it.statusRequest) {
                StatusRequest.LOADING -> {
                    dialogLoading.show()
                }
                StatusRequest.EMPTY -> {
                    dialogLoading.dismiss()
                    showDialogEmpty("Tidak ditemukan post")
                }
                StatusRequest.SUCCESS -> {
                    dialogLoading.dismiss()
                    setupSuccess()
                }
                StatusRequest.ERROR->{
                    dialogLoading.dismiss()
                    showDialogError(it.failureModel?.messageShow.toString())
                }
            }
        })
    }
    private fun setupSuccess() {
        TODO("Bikin Success")
    }

    private fun showDialogError(message: String) {
        val dialogError = DialogMessage(
            this,
            StatusView.ERROR,
            "Terjadi Kesalahan",
            message,
            object : DialogMessage.DialogMesageListener {
                override fun onButtonClicked() {
                    mainViewModel.getPosts()
                }

                override fun onBackPressedClicked() {
                    finish()
                }
            })
        dialogError.setCancelable(false)
        dialogError.show()
    }

    private fun showDialogEmpty(message: String) {
        val dialogError = DialogMessage(
            this,
            StatusView.EMPTY,
            "Ups..",
            message,
            object : DialogMessage.DialogMesageListener {
                override fun onButtonClicked() {
                    mainViewModel.getPosts()
                }

                override fun onBackPressedClicked() {
                    finish()
                }
            })

        dialogError.setCancelable(false)
        dialogError.show()

    }

}