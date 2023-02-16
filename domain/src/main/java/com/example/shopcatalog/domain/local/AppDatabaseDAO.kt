package com.example.shopcatalog.domain.local

import android.net.Uri
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shopcatalog.domain.local.entities.Users
import com.example.shopcatalog.domain.model.CurrentUserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDatabaseDAO {

    @Insert
    suspend fun importUserInfo(userInfo: Users)

    @Query("SELECT * from users where email = :email")
    fun fetchAllInfo(email: String): Flow<Users>

    @Query("UPDATE users SET gender = :value WHERE email = :email")
    suspend fun updateUserGender(value: String, email: String)

    @Query("UPDATE users SET phoneNumber = :value WHERE email = :email")
    suspend fun updateUserPhoneNumber(value: String, email: String)

    @Query("UPDATE users SET profileImage = :value WHERE email = :email")
    suspend fun updateUserProfileImage(value: Uri, email: String)
}