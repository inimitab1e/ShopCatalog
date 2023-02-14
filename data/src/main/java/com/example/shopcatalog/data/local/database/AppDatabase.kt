package com.example.shopcatalog.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shopcatalog.domain.local.AppDatabaseDAO
import com.example.shopcatalog.domain.local.entities.Cart
import com.example.shopcatalog.domain.local.entities.Users

@Database(entities = [Users::class, Cart::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun AppDatabaseDAO(): AppDatabaseDAO
}