package com.example.marcossanchez.ui.common

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes

class StringProvider(val context: Context)  {
    companion object {
        fun instance(context: Context): StringProvider = StringProvider(context)
    }

}