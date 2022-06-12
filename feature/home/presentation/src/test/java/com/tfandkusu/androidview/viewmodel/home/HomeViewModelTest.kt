package com.tfandkusu.androidview.viewmodel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.ads.nativead.NativeAd
import com.tfandkusu.androidview.catalog.GitHubRepoCatalog
import com.tfandkusu.androidview.error.NetworkErrorException
import com.tfandkusu.androidview.usecase.home.HomeLoadUseCase
import com.tfandkusu.androidview.usecase.home.HomeOnCreateUseCase
import com.tfandkusu.androidview.viewmodel.error.ApiErrorState
import com.tfandkusu.androidview.viewmodel.mockStateObserver
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK(relaxed = true)
    private lateinit var loadUseCase: HomeLoadUseCase

    @MockK(relaxed = true)
    private lateinit var onCreateUseCase: HomeOnCreateUseCase

    private lateinit var viewModel: HomeViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = HomeViewModelImpl(loadUseCase, onCreateUseCase)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun onCreate() = runTest {
        val repos = GitHubRepoCatalog.getList()
        every {
            onCreateUseCase.execute()
        } returns flow {
            emit(repos)
        }
        val mockStateObserver = viewModel.state.mockStateObserver()
        viewModel.event(HomeEvent.OnCreate)
        viewModel.effect.first() shouldBe HomeEffect.LoadNativeAds
        verifySequence {
            mockStateObserver.onChanged(HomeState())
            onCreateUseCase.execute()
            mockStateObserver.onChanged(HomeState(repos = repos))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadSuccess() = runTest {
        val stateMockObserver = viewModel.state.mockStateObserver()
        val errorStateMockObserver = viewModel.error.state.mockStateObserver()
        viewModel.event(HomeEvent.Load)
        coVerifySequence {
            stateMockObserver.onChanged(HomeState())
            errorStateMockObserver.onChanged(ApiErrorState())
            errorStateMockObserver.onChanged(ApiErrorState())
            stateMockObserver.onChanged(HomeState(progress = true))
            loadUseCase.execute()
            stateMockObserver.onChanged(HomeState(progress = false))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadError() = runTest {
        coEvery {
            loadUseCase.execute()
        } throws NetworkErrorException()
        val stateMockObserver = viewModel.state.mockStateObserver()
        val errorMockStateObserver = viewModel.error.state.mockStateObserver()
        viewModel.event(HomeEvent.Load)
        coVerifySequence {
            stateMockObserver.onChanged(HomeState())
            errorMockStateObserver.onChanged(ApiErrorState())
            errorMockStateObserver.onChanged(ApiErrorState())
            stateMockObserver.onChanged(HomeState(progress = true))
            loadUseCase.execute()
            errorMockStateObserver.onChanged(ApiErrorState(network = true))
            stateMockObserver.onChanged(HomeState(progress = false))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadNativeAdSuccess() = runTest {
        val stateMockObserver = viewModel.state.mockStateObserver()
        val ad1 = mockk<NativeAd>()
        val ad2 = mockk<NativeAd>()
        val ad3 = mockk<NativeAd>()
        viewModel.event(HomeEvent.LoadNativeAd(ad1))
        viewModel.event(HomeEvent.LoadNativeAd(ad2))
        viewModel.event(HomeEvent.LoadNativeAd(ad3))
        viewModel.event(HomeEvent.EndLoadNativeAd)
        verifySequence {
            stateMockObserver.onChanged(HomeState())
            stateMockObserver.onChanged(
                HomeState(
                    nativeAds = listOf(
                        HomeNativeAd(1, ad1), HomeNativeAd(2), HomeNativeAd(3)
                    )
                )
            )
            stateMockObserver.onChanged(
                HomeState(
                    nativeAds = listOf(
                        HomeNativeAd(1, ad1), HomeNativeAd(2, ad2), HomeNativeAd(3)
                    )
                )
            )
            stateMockObserver.onChanged(
                HomeState(
                    nativeAds = listOf(
                        HomeNativeAd(1, ad1), HomeNativeAd(2, ad2), HomeNativeAd(3, ad3)
                    )
                )
            )
            stateMockObserver.onChanged(
                HomeState(
                    nativeAds = listOf(
                        HomeNativeAd(1, ad1), HomeNativeAd(2, ad2), HomeNativeAd(3, ad3)
                    )
                )
            )
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadNativeAdFailed() = runTest {
        val stateMockObserver = viewModel.state.mockStateObserver()
        val ad1 = mockk<NativeAd>()
        viewModel.event(HomeEvent.LoadNativeAd(ad1))
        viewModel.event(HomeEvent.EndLoadNativeAd)
        verifySequence {
            stateMockObserver.onChanged(HomeState())
            stateMockObserver.onChanged(
                HomeState(
                    nativeAds = listOf(
                        HomeNativeAd(1, ad1), HomeNativeAd(2), HomeNativeAd(3)
                    )
                )
            )
            stateMockObserver.onChanged(
                HomeState(
                    nativeAds = listOf(
                        HomeNativeAd(1, ad1),
                        HomeNativeAd(2, failedToLoad = true),
                        HomeNativeAd(3, failedToLoad = true)
                    )
                )
            )
        }
    }
}
