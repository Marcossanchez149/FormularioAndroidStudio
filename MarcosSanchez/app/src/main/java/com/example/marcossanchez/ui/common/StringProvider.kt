package com.example.marcossanchez.ui.common

import android.content.Context

class StringProvider(val context: Context)  {
    companion object {
        fun instance(context: Context): StringProvider = StringProvider(context)
    }

}