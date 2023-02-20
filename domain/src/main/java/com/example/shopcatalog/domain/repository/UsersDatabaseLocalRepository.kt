package com.example.shopcatalog.domain.repository

import android.net.Uri
import com.example.shopcatalog.domain.model.CurrentUserInfo
import kotlinx.coroutines.flow.Flow

interface UsersDatabaseLocalRepository {

    fun insertInitialUserInfoToDatabase(email: String, userName: String)

    fun fetchAllUserInfo(email: String) : Flow<CurrentUserInfo>

    suspend fun updateUserGenderInDatabase(value: String, email: String)

    suspend fun updateUserPhoneNumberInDatabase(value: String, email: String)

    suspend fun updateUserProfileAvatarInDatabase(value: Uri, email: String)
}