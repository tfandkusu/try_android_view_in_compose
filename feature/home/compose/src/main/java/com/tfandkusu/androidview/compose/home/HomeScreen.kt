package com.tfandkusu.androidview.compose.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.tfandkusu.androidview.catalog.GitHubRepoCatalog
import com.tfandkusu.androidview.compose.NyTopAppBar
import com.tfandkusu.androidview.compose.home.listitem.GitHubRepoListItem
import com.tfandkusu.androidview.compose.home.listitem.InfeedAdMobNativeAdvancedAndroidView
import com.tfandkusu.androidview.home.compose.R
import com.tfandkusu.androidview.ui.theme.MyTheme
import com.tfandkusu.androidview.view.error.ApiError
import com.tfandkusu.androidview.view.info.InfoActivityAlias
import com.tfandkusu.androidview.viewmodel.error.ApiErrorViewModelHelper
import com.tfandkusu.androidview.viewmodel.error.useErrorState
import com.tfandkusu.androidview.viewmodel.home.HomeEffect
import com.tfandkusu.androidview.viewmodel.home.HomeEvent
import com.tfandkusu.androidview.viewmodel.home.HomeState
import com.tfandkusu.androidview.viewmodel.home.HomeViewModel
import com.tfandkusu.androidview.viewmodel.requireValue
import com.tfandkusu.androidview.viewmodel.useState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.parcelize.Parcelize

@Parcelize
class HomeScreenItemId(
    val repoId: Long,
    val adIndex: Int
) : Parcelable

@Composable
fun HomeScreen(viewModel: HomeViewModel, navigateToDetail: () -> Unit = {}) {
    val state = useState(viewModel)
    val errorState = useErrorState(viewModel.error)
    val context = LocalContext.current
    val recycler = remember {
        InfeedAndroidViewRecycler()
    }
    val unitId = stringResource(R.string.ad_mob_native_advanced_unit_id)
    LaunchedEffect(Unit) {
        viewModel.event(HomeEvent.OnCreate)
        viewModel.event(HomeEvent.Load)
        viewModel.effect.collect {
            if (it == HomeEffect.LoadNativeAds) {
                lateinit var adLoader: AdLoader
                adLoader = AdLoader.Builder(context, unitId)
                    .forNativeAd { ad ->
                        viewModel.event(HomeEvent.LoadNativeAd(ad))
                        if (!adLoader.isLoading) {
                            viewModel.event(HomeEvent.EndLoadNativeAd)
                        }
                    }.withAdListener(object : AdListener() {
                        override fun onAdFailedToLoad(error: LoadAdError) {
                            viewModel.event(HomeEvent.EndLoadNativeAd)
                        }
                    }).withNativeAdOptions(
                        NativeAdOptions.Builder()
                            .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                            .setMediaAspectRatio(NativeAdOptions.NATIVE_MEDIA_ASPECT_RATIO_SQUARE)
                            .build()
                    )
                    .build()
                adLoader.loadAds(AdRequest.Builder().build(), state.nativeAds.size)
            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            recycler.clear()
            viewModel.state.requireValue().nativeAds.map {
                it.nativeAd?.destroy()
            }
        }
    }
    Scaffold(
        topBar = {
            NyTopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(context, InfoActivityAlias::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.action_information)
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            if (errorState.noError()) {
                if (state.progress) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        state.repos.mapIndexed { index, repo ->
                            item(key = HomeScreenItemId(repo.id, 0)) {
                                GitHubRepoListItem(repo) {
                                    navigateToDetail()
                                }
                            }
                            if ((index - 2) % 7 == 0) {
                                item(key = HomeScreenItemId(0, index)) {
                                    val adIndex = (index - 2) / 7
                                    val nativeAd = state.nativeAds[adIndex % state.nativeAds.size]
                                    InfeedAdMobNativeAdvancedAndroidView(
                                        nativeAd.nativeAd
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                ApiError(errorState) {
                    viewModel.event(HomeEvent.Load)
                }
            }
            // BottomAdMobAndroidView()
        }
    }
}

class HomeViewModelPreview(private val previewState: HomeState) : HomeViewModel {
    override val error: ApiErrorViewModelHelper
        get() = ApiErrorViewModelHelper()

    override fun createDefaultState() = previewState

    override val state: LiveData<HomeState>
        get() = MutableLiveData(createDefaultState())

    override val effect: Flow<HomeEffect>
        get() = flow {}

    override fun event(event: HomeEvent) {
    }
}

@Composable
@Preview
fun HomeScreenPreviewProgress() {
    MyTheme {
        HomeScreen(HomeViewModelPreview(HomeState()))
    }
}

@Composable
@Preview
fun HomeScreenPreviewList() {
    val repos = GitHubRepoCatalog.getList()
    val state = HomeState(
        progress = false,
        repos = repos
    )
    MyTheme {
        HomeScreen(HomeViewModelPreview(state))
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HomeScreenPreviewDarkProgress() {
    MyTheme {
        HomeScreen(HomeViewModelPreview(HomeState()))
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HomeScreenPreviewDarkList() {
    val repos = GitHubRepoCatalog.getList()
    val state = HomeState(
        progress = false,
        repos = repos
    )
    MyTheme {
        HomeScreen(HomeViewModelPreview(state))
    }
}
