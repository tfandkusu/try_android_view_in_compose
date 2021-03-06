package com.tfandkusu.androidview.viewmodel.home

import com.tfandkusu.androidview.model.GithubRepo
import com.tfandkusu.androidview.viewmodel.UnidirectionalViewModel
import com.tfandkusu.androidview.viewmodel.error.ApiErrorViewModelHelper

sealed class HomeEvent {

    object OnCreate : HomeEvent()

    object Load : HomeEvent()
}

sealed class HomeEffect

data class HomeState(
    val progress: Boolean = true,
    val repos: List<GithubRepo> = listOf()
)

interface HomeViewModel : UnidirectionalViewModel<HomeEvent, HomeEffect, HomeState> {
    val error: ApiErrorViewModelHelper
}
