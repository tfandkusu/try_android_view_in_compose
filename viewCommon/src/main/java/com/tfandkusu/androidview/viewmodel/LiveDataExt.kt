package com.tfandkusu.androidview.viewmodel

import androidx.lifecycle.LiveData

fun <T : Any> LiveData<T>.requireValue() = requireNotNull(value)
