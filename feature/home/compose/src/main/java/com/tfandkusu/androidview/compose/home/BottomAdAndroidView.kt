package com.tfandkusu.androidview.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.tfandkusu.androidview.home.compose.R
import com.tfandkusu.androidview.home.compose.databinding.ViewBottomAdBinding
import timber.log.Timber

@Composable
fun BottomAdAndroidView() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(color = colorResource(R.color.ad_background))
            .padding(top = 1.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        AndroidViewBinding(
            modifier = Modifier.width(320.dp).height(50.dp).fillMaxWidth(),
            factory = { inflater, parent, _ ->
                Timber.d("factory")
                ViewBottomAdBinding.inflate(inflater, parent, false)
            },
            update = {
                Timber.d("update")
            }
        )
    }
}
