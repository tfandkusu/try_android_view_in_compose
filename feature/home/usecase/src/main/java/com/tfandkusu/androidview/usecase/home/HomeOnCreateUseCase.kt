package com.tfandkusu.androidview.usecase.home

import com.tfandkusu.androidview.data.repository.GithubRepoRepository
import com.tfandkusu.androidview.model.GithubRepo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface HomeOnCreateUseCase {
    fun execute(): Flow<List<GithubRepo>>
}

class HomeOnCreateUseCaseImpl @Inject constructor(
    private val repository: GithubRepoRepository
) : HomeOnCreateUseCase {
    override fun execute() = repository.listAsFlow()
}
