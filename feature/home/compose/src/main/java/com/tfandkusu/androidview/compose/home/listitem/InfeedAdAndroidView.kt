package com.tfandkusu.androidview.compose.home.listitem

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.tfandkusu.androidview.compose.home.InfeedAndroidViewRecycler
import com.tfandkusu.androidview.home.compose.R
import com.tfandkusu.androidview.home.compose.databinding.ViewInfeedAdBinding
import timber.log.Timber

enum class AdType(val type: Int) {
    TYPE_1(1), TYPE_2(2)
}

@Composable
fun InfeedAdAndroidView(adType: AdType, recycler: InfeedAndroidViewRecycler) {
    Column {
        AndroidViewBinding(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            factory = { inflater, parent, _ ->
                recycler.recycle()?.let { view ->
                    Timber.d("factory bind")
                    ViewInfeedAdBinding.bind(view)
                } ?: run {
                    Timber.d("factory inflate")
                    val binding = ViewInfeedAdBinding.inflate(inflater, parent, false)
                    binding.root.addOnAttachStateChangeListener(
                        object : View.OnAttachStateChangeListener {
                            override fun onViewAttachedToWindow(v: View) {
                            }

                            override fun onViewDetachedFromWindow(view: View) {
                                recycler.save(view)
                            }
                        })
                    binding
                }
            },
            update = {
                Timber.d("update $adType")
                when (adType) {
                    AdType.TYPE_1 -> {
                        this.image.setImageResource(R.drawable.animal_wallaby_kangaroo)
                        this.title.setText(R.string.infeed_ad_1_title)
                        this.message.setText(R.string.infeed_ad_1_message)
                    }
                    AdType.TYPE_2 -> {
                        this.image.setImageResource(R.drawable.penguin16_humboldt)
                        this.title.setText(R.string.infeed_ad_2_title)
                        this.message.setText(R.string.infeed_ad_2_message)
                    }
                }
            }
        )
        Divider()
    }
}
