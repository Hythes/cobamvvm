package com.hythes.jsonplaceholder.application.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.hythes.jsonplaceholder.application.ui.dialog.DialogLoading

abstract class BaseActivity : AppCompatActivity() {
    lateinit var dialogLoading: DialogLoading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialogLoading = DialogLoading(this, "Loading....")
        dialogLoading.setCancelable(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}