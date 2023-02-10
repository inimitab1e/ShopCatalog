package com.example.shopcatalog.di

import android.content.Context
import com.example.shopcatalog.data.AppDispatchers
import com.example.shopcatalog.data.network.AuthService
import com.example.shopcatalog.data.repository.LaunchAppRepositoryImpl
import com.example.shopcatalog.domain.repository.LaunchAppRepository
import com.example.shopcatalog.domain.security.PrefHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideLaunchAppRepository(
        authService: AuthService,
        appDispatchers: AppDispatchers
    ): LaunchAppRepository {
        return LaunchAppRepositoryImpl(authService = authService, appDispatchers = appDispatchers)
    }

    @Provides
    @Singleton
    fun providePrefHelper(@ApplicationContext context: Context): PrefHelper {
        return PrefHelper(context = context)
    }
}