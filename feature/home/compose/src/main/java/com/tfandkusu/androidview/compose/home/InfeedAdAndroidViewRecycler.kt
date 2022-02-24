package com.tfandkusu.androidview.compose.home

import android.view.View
import timber.log.Timber

class InfeedAdAndroidViewRecycler {
    private val queue = ArrayDeque(listOf<View>())

    fun save(view: View) {
        Timber.d("save")
        queue.add(view)
    }

    fun recycle(): View? {
        Timber.d("recycle")
        return if (queue.isEmpty()) {
            null
        } else {
            queue.removeFirst()
        }
    }

    fun clear() {
        Timber.d("clear")
        queue.clear()
    }
}
