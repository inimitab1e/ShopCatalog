package com.example.shopcatalog.di

import android.content.Context
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
    fun providePrefHelper(@ApplicationContext context: Context) : PrefHelper {
        return PrefHelper(context = context)
    }
}