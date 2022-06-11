package com.tfandkusu.androidview.compose.home.listitem

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tfandkusu.androidview.home.compose.R

@SuppressLint("MissingPermission")
@Composable
fun InfeedAdMobAndroidViewSimple() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            val unitId = stringResource(R.string.ad_mob_banner_unit_id)
            AndroidView(
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp),
                factory = { context ->
                    val adView = AdView(context)
                    adView.setAdSize(AdSize.BANNER)
                    adView.adUnitId = unitId
                    val adRequest = AdRequest.Builder().build()
                    adView.loadAd(adRequest)
                    adView
                }
            )
        }
        Divider()
    }
}
