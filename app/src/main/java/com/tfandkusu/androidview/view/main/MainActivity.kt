package com.tfandkusu.androidview.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.google.android.gms.ads.AdRequest
import com.tfandkusu.androidview.databinding.ActivityMainBinding
import com.tfandkusu.androidview.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.composeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                this
            )
        )
        binding.composeView.setContent {
            MyTheme {
                AppContent()
            }
        }
    }
}
