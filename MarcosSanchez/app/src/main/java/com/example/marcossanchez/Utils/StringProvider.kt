package com.example.marcossanchez.Utils

import android.content.Context
import androidx.annotation.StringRes

class StringProvider(val context: Context) {
    companion object {
        fun instance(context: Context): StringProvider = StringProvider(context)
    }

    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

}