package com.tfandkusu.androidview.viewmodel.home

import com.google.android.gms.ads.nativead.NativeAd
import com.tfandkusu.androidview.model.GithubRepo
import com.tfandkusu.androidview.viewmodel.UnidirectionalViewModel
import com.tfandkusu.androidview.viewmodel.error.ApiErrorViewModelHelper

sealed class HomeEvent {

    object OnCreate : HomeEvent()

    object Load : HomeEvent()

    data class LoadNativeAd(val nativeAd: NativeAd) : HomeEvent()

    object EndLoadNativeAd : HomeEvent()
}

sealed class HomeEffect {
    object LoadNativeAds : HomeEffect()
}

data class HomeNativeAd(
    val id: Int,
    val nativeAd: NativeAd? = null,
    val failedToLoad: Boolean = false
) {
    fun isEnd(): Boolean {
        return nativeAd != null || failedToLoad
    }
}

data class HomeState(
    val progress: Boolean = true,
    val repos: List<GithubRepo> = listOf(),
    val nativeAds: List<HomeNativeAd> = listOf(
        HomeNativeAd(1), HomeNativeAd(2), HomeNativeAd(3)
    )
)

interface HomeViewModel : UnidirectionalViewModel<HomeEvent, HomeEffect, HomeState> {
    val error: ApiErrorViewModelHelper
}
