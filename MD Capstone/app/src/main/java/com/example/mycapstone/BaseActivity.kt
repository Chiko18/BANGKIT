package com.example.mycapstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
    fun showErrorSnackbar(message: String, errorMessage: Boolean){
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view

        if (errorMessage){
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity, R.color.colorSnackBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity, R.color.colorAnackBarSuccess
                )
            )
        }
        snackbar.show()
    }

}