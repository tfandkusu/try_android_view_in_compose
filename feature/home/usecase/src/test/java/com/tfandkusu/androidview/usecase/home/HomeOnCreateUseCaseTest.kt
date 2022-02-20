package com.tfandkusu.androidview.usecase.home

import com.tfandkusu.androidview.catalog.GitHubRepoCatalog
import com.tfandkusu.androidview.data.repository.GithubRepoRepository
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HomeOnCreateUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var repository: GithubRepoRepository

    private lateinit var useCase: HomeOnCreateUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = HomeOnCreateUseCaseImpl(repository)
    }

    @Test
    fun execute() = runBlocking {
        val repos = GitHubRepoCatalog.getList()
        every {
            repository.listAsFlow()
        } returns flow {
            emit(repos)
        }
        useCase.execute().first() shouldBe repos
        verifySequence {
            repository.listAsFlow()
        }
    }
}
