package com.example.shopcatalog.data.repository

import android.net.Uri
import com.example.shopcatalog.data.AppDispatchers
import com.example.shopcatalog.domain.local.AppDatabaseDAO
import com.example.shopcatalog.domain.local.entities.Users
import com.example.shopcatalog.domain.repository.UsersDatabaseLocalRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersDatabaseLocalRepositoryImpl @Inject constructor(
    private val appDatabaseDAO: AppDatabaseDAO,
    private val appDispatchers: AppDispatchers
) : UsersDatabaseLocalRepository {

    override suspend fun insertInitialUserInfoToDatabase(email: String, userName: String) {
        withContext(appDispatchers.io) {
            appDatabaseDAO.importUserInfo(
                Users(
                    email = email,
                    userName = userName,
                    gender = null,
                    phoneNumber = null,
                    profileImage = null
                )
            )
        }
    }

    override suspend fun updateUserGenderInDatabase(value: String, email: String) {
        withContext(appDispatchers.io) {
            appDatabaseDAO.updateUserGender(value, email)
        }
    }

    override suspend fun updateUserPhoneNumberInDatabase(value: String, email: String) {
        withContext(appDispatchers.io) {
            appDatabaseDAO.updateUserPhoneNumber(value, email)
        }
    }

    override suspend fun updateUserProfileAvatarInDatabase(value: Uri, email: String) {
        withContext(appDispatchers.io) {
            appDatabaseDAO.updateUserProfileImage(value, email)
        }
    }
}