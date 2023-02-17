package com.example.shopcatalog.di

import android.content.Context
import androidx.room.Room
import com.example.shopcatalog.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "shop_catalog.db"
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
}