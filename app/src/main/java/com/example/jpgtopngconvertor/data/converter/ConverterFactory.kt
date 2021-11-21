package com.example.jpgtopngconvertor.data.converter

import android.content.Context
import android.net.Uri

object ConverterFactory {
    fun create(context: Context): ConverterImpl = ConverterImpl(context)
}