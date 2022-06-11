package com.tfandkusu.androidview.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.tfandkusu.androidview.R
import com.tfandkusu.androidview.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        val composeView = findViewById<ComposeView>(R.id.coomposeView)
        composeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                this
            )
        )
        composeView.setContent {
            MyTheme {
                AppContent()
            }
        }
    }
}
