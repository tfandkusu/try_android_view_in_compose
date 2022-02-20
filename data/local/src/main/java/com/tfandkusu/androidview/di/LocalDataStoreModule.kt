package com.tfandkusu.androidview.di

import com.tfandkusu.androidview.data.local.CreatedLocalDataStore
import com.tfandkusu.androidview.data.local.CreatedLocalDataStoreImpl
import com.tfandkusu.androidview.data.local.GithubRepoLocalDataStore
import com.tfandkusu.androidview.data.local.GithubRepoLocalDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataStoreModule {
    @Binds
    @Singleton
    abstract fun provideGithubRepoLocalDataStore(
        localDataStore: GithubRepoLocalDataStoreImpl
    ): GithubRepoLocalDataStore

    @Binds
    @Singleton
    abstract fun provideCreatedLocalDataStore(
        localDataStore: CreatedLocalDataStoreImpl
    ): CreatedLocalDataStore
}
