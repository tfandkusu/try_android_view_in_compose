package com.tfandkusu.androidview.compose.home

import android.view.View

class InfeedAdAndroidViewRecycler {
    private val queue = ArrayDeque(listOf<View>())

    fun save(view: View) {
        queue.add(view)
    }

    fun recycle(): View? {
        return if (queue.isEmpty()) {
            null
        } else {
            queue.removeFirst()
        }
    }

    fun clear() {
        queue.clear()
    }
}
