package com.tfandkusu.androidview.view.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tfandkusu.androidview.compose.home.HomeScreen
import com.tfandkusu.androidview.compose.home.LocalInfeedAdAndroidViewRecycler
import com.tfandkusu.androidview.viewmodel.home.HomeViewModelImpl

private const val HOME_PATH = "home"

@Composable
fun AppContent() {
    val recycler = LocalInfeedAdAndroidViewRecycler.current
    DisposableEffect(Unit) {
        onDispose {
            recycler.clear()
        }
    }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME_PATH) {
        composable(HOME_PATH) {
            val viewModel = hiltViewModel<HomeViewModelImpl>()
            HomeScreen(viewModel)
        }
    }
}
