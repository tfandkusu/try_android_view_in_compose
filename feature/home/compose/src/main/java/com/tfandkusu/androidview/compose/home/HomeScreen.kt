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
import com.tfandkusu.androidview.catalog.GitHubRepoCatalog
import com.tfandkusu.androidview.compose.NyTopAppBar
import com.tfandkusu.androidview.compose.home.listitem.GitHubRepoListItem
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
    val recycler = remember {
        InfeedAdAndroidViewRecycler()
    }
    LaunchedEffect(Unit) {
        viewModel.event(HomeEvent.OnCreate)
        viewModel.event(HomeEvent.Load)
    }
    DisposableEffect(Unit) {
        onDispose {
            recycler.clear()
        }
    }
    val context = LocalContext.current
    val state = useState(viewModel)
    val errorState = useErrorState(viewModel.error)
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
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        var adIndex = 0
                        state.repos.mapIndexed { index, repo ->
                            item(key = HomeScreenItemId(repo.id, 0)) {
                                GitHubRepoListItem(repo) {
                                    navigateToDetail()
                                }
                            }
                            if ((index - 2) % 7 == 0) {
                                val adType = if ((index - 2) % 14 == 0)
                                    AdType.TYPE_1
                                else
                                    AdType.TYPE_2
                                item(key = HomeScreenItemId(0, adIndex)) {
                                    InfeedAdAndroidView(adType, recycler)
                                }
                                adIndex += 1
                            }
                        }
                    }
                }
            } else {
                ApiError(errorState) {
                    viewModel.event(HomeEvent.Load)
                }
            }
            BottomAdAndroidView()
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
