package com.nikodem.crypto.utils

import android.content.Context
import androidx.annotation.StringRes

interface ContentProvider {
    fun getString(@StringRes strResId: Int): String
}

class ContentProviderImpl(
    private val context: Context
) : ContentProvider {
    override fun getString(strResId: Int): String {
        return context.getString(strResId)
    }
}