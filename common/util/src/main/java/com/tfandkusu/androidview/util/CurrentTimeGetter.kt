package com.tfandkusu.androidview.util

import javax.inject.Inject

interface CurrentTimeGetter {
    fun get(): Long
}

class CurrentTimeGetterImpl @Inject constructor() : CurrentTimeGetter {
    override fun get() = System.currentTimeMillis()
}
