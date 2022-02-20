package com.tfandkusu.androidview.di

import com.tfandkusu.androidview.data.remote.GithubRemoteDataStore
import com.tfandkusu.androidview.data.remote.GithubRemoteDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataStoreModule {
    @Binds
    @Singleton
    abstract fun provideGithubRemoteDataStore(
        remoteDataStore: GithubRemoteDataStoreImpl
    ): GithubRemoteDataStore
}
