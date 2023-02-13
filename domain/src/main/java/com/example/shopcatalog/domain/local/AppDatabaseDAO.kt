package com.example.shopcatalog.domain.local

import androidx.room.Dao
import androidx.room.Insert
import com.example.shopcatalog.domain.local.entities.Users

@Dao
interface AppDatabaseDAO {

    @Insert
    suspend fun importUserInfo(userInfo: Users)
}