package com.example.shopcatalog.domain.repository

import android.net.Uri

interface UsersDatabaseLocalRepository {

    suspend fun insertInitialUserInfoToDatabase(email: String, userName: String)

    suspend fun updateUserGenderInDatabase(value: String, email: String)

    suspend fun updateUserPhoneNumberInDatabase(value: String, email: String)

    suspend fun updateUserProfileAvatarInDatabase(value: Uri, email: String)
}