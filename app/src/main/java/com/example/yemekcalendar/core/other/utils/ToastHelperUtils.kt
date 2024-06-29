package com.example.yemekcalendar.core.other.utils

import android.content.Context
import android.widget.Toast

object ToastHelper {
    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun showToast(message: String) {
        appContext?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }
}

