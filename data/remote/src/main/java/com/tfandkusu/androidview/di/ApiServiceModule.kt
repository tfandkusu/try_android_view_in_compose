package com.tfandkusu.androidview.di

import com.tfandkusu.androidview.api.TemplateApiServiceBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    @Singleton
    fun providesTemplateApiService() = TemplateApiServiceBuilder.build()
}
