package com.tfandkusu.androidview.compose.home.listitem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.gms.ads.nativead.NativeAd
import com.tfandkusu.androidview.home.compose.databinding.ViewNativeAdBinding

@Composable
fun InfeedAdMobNativeAdvancedAndroidView(ad: NativeAd?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (ad != null) {
            AndroidViewBinding(factory = { inflater, parent, _ ->
                val binding = ViewNativeAdBinding.inflate(inflater, parent, false)
                binding.adHeadline.text = ad.headline
                binding.adView.headlineView = binding.adHeadline
                binding.adView.setNativeAd(ad)
                binding
            })
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            )
        }
        Divider()
    }
}
