package com.tfandkusu.androidview.usecase.home

import com.tfandkusu.androidview.data.repository.GithubRepoRepository
import javax.inject.Inject

interface HomeLoadUseCase {
    suspend fun execute()
}

class HomeLoadUseCaseImpl @Inject constructor(
    private val repository: GithubRepoRepository
) : HomeLoadUseCase {
    override suspend fun execute() {
        if (repository.isCacheExpired()) {
            repository.fetch()
        }
    }
}
