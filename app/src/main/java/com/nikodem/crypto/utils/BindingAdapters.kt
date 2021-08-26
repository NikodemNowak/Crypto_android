package com.nikodem.crypto.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("onClick")
fun View.setOnClick(action: () -> Unit) {
    setOnClickListener {
        action.invoke()
    }
}

@BindingAdapter("android:htmlText")
fun setHtmlTextValue(textView: TextView, htmlText: String?) {
    if (htmlText == null) return
    val result: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(htmlText)
    }
    textView.text = result
}