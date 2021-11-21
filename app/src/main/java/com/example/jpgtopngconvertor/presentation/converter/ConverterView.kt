package com.example.jpgtopngconvertor.presentation.converter

import android.net.Uri
import com.example.jpgtopngconvertor.presentation.ScreenView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface ConverterView : ScreenView {
    @AddToEndSingle
    fun showContent(uri: Uri?)

    @AddToEndSingle
    fun showLoading()
}