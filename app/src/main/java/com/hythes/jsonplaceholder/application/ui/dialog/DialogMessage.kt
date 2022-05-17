package com.hythes.jsonplaceholder.application.ui.dialog

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import com.hythes.jsonplaceholder.application.util.StatusView
import com.hythes.jsonplaceholder.databinding.DialogMessageBinding


class DialogMessage(
    val myContext: Context,
    val status: StatusView,
    val title: String,
    val message: String,
    val listener: DialogMesageListener
) : Dialog(myContext) {

    private lateinit var binding : DialogMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DialogMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)

        when(status){
            StatusView.LOADING -> {
                binding.dmLottie.visibility = View.GONE
            }
            StatusView.EMPTY -> {
                binding.dmLottie.setAnimation("lottie_empty.json")
                binding.dmLottie.playAnimation()
            }
            StatusView.ERROR -> {
                binding.dmLottie.setAnimation("lottie_error.json")
                binding.dmLottie.playAnimation()
            }
            StatusView.INFORMATION -> {
                binding.dmLottie.visibility = View.GONE
            }
        }

        binding.dmTvTitle.text = title
        binding.dmTvContent.text = message
        binding.dmBtnTryAgain.setOnClickListener {
            dismiss()
            listener.onButtonClicked()
        }
    }

    interface DialogMesageListener {
        fun onButtonClicked()
        fun onBackPressedClicked()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        dismiss()
        listener.onBackPressedClicked()
    }

}