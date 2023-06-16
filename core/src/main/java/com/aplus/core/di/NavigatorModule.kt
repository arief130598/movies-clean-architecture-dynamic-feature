package com.aplus.core.di

import com.aplus.core.utils.NavigationHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigatorModule {

    @Provides
    @Singleton
    fun provideNavigator() : NavigationHelper {
        return NavigationHelper()
    }
}