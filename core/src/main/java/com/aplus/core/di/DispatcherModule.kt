package com.aplus.core.di

import com.aplus.core.utils.DispatcherHelper
import com.aplus.core.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcherHelper(): DispatcherProvider {
        return DispatcherHelper()
    }
}