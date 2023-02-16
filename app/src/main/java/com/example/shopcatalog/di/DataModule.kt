package com.example.shopcatalog.di

import android.content.Context
import androidx.room.Room
import com.example.shopcatalog.data.AppDispatchers
import com.example.shopcatalog.data.local.database.AppDatabase
import com.example.shopcatalog.data.network.AuthService
import com.example.shopcatalog.data.repository.AuthenticationRepositoryImpl
import com.example.shopcatalog.data.repository.CatalogRepositoryImpl
import com.example.shopcatalog.data.repository.LaunchAppRepositoryImpl
import com.example.shopcatalog.data.repository.UsersDatabaseLocalRepositoryImpl
import com.example.shopcatalog.domain.local.AppDatabaseDAO
import com.example.shopcatalog.domain.repository.AuthenticationRepository
import com.example.shopcatalog.domain.repository.CatalogRepository
import com.example.shopcatalog.domain.repository.LaunchAppRepository
import com.example.shopcatalog.domain.repository.UsersDatabaseLocalRepository
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
    ): LaunchAppRepository =
        LaunchAppRepositoryImpl(authService = authService, appDispatchers = appDispatchers)

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        authService: AuthService,
        appDispatchers: AppDispatchers
    ): AuthenticationRepository =
        AuthenticationRepositoryImpl(authService = authService, appDispatchers = appDispatchers)

    @Provides
    @Singleton
    fun provideCatalogRepository(@ApplicationContext context: Context): CatalogRepository =
        CatalogRepositoryImpl(context = context)

    @Provides
    @Singleton
    fun provideUserDatabaseLocalRepository(
        appDatabaseDAO: AppDatabaseDAO,
        appDispatchers: AppDispatchers
    ): UsersDatabaseLocalRepository =
        UsersDatabaseLocalRepositoryImpl(
            appDatabaseDAO = appDatabaseDAO,
            appDispatchers = appDispatchers
        )

    @Provides
    @Singleton
    fun provideAppDatabaseDao(db: AppDatabase): AppDatabaseDAO = db.AppDatabaseDAO()

    @Provides
    @Singleton
    fun providePrefHelper(@ApplicationContext context: Context): PrefHelper =
        PrefHelper(context = context)
}