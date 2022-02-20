package com.tfandkusu.androidview.di

import com.tfandkusu.androidview.usecase.home.HomeLoadUseCase
import com.tfandkusu.androidview.usecase.home.HomeLoadUseCaseImpl
import com.tfandkusu.androidview.usecase.home.HomeOnCreateUseCase
import com.tfandkusu.androidview.usecase.home.HomeOnCreateUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    @Binds
    @Singleton
    abstract fun bindHomeLoadUseCase(
        useCase: HomeLoadUseCaseImpl
    ): HomeLoadUseCase

    @Binds
    @Singleton
    abstract fun bindOnCreateUseCase(
        useCase: HomeOnCreateUseCaseImpl
    ): HomeOnCreateUseCase
}
