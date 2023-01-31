package com.example.fitnesskittesttask.util

import android.content.Context

class ResourcesProviderImpl(
    private val context: Context
) : ResourcesProvider {

    override fun getString(resId: Int): String = context.getString(resId)
}