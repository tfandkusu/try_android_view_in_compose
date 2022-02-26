package com.tfandkusu.androidview.view.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tfandkusu.androidview.compose.detail.DetailScreen
import com.tfandkusu.androidview.compose.home.HomeScreen
import com.tfandkusu.androidview.viewmodel.home.HomeViewModelImpl

private const val HOME_PATH = "home"

private const val DETAIL_PATH = "detail"

@Composable
fun AppContent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME_PATH) {
        composable(HOME_PATH) {
            val viewModel = hiltViewModel<HomeViewModelImpl>()
            HomeScreen(viewModel) {
                navController.navigate(DETAIL_PATH)
            }
        }
        composable(DETAIL_PATH) {
            DetailScreen {
                navController.popBackStack()
            }
        }
    }
}
