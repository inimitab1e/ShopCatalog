package com.example.shopcatalog.data.repository

import android.net.Uri
import com.example.shopcatalog.data.toCurrentUserInfo
import com.example.shopcatalog.domain.local.AppDatabaseDAO
import com.example.shopcatalog.domain.local.entities.Users
import com.example.shopcatalog.domain.model.CurrentUserInfo
import com.example.shopcatalog.domain.repository.UsersDatabaseLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersDatabaseLocalRepositoryImpl @Inject constructor(
    private val appDatabaseDAO: AppDatabaseDAO,
    private val ioDispatcher: CoroutineDispatcher
) : UsersDatabaseLocalRepository {

    override fun insertInitialUserInfoToDatabase(email: String, userName: String) =
        appDatabaseDAO.importUserInfo(
            Users(
                email = email,
                userName = userName,
                gender = null,
                phoneNumber = null,
                profileImage = null
            )
        )


    override fun fetchAllUserInfo(email: String): Flow<CurrentUserInfo> =
        appDatabaseDAO.fetchAllInfo(email)
            .map { value -> value.toCurrentUserInfo() }


    override suspend fun updateUserGenderInDatabase(value: String, email: String) {
        withContext(ioDispatcher) {
            appDatabaseDAO.updateUserGender(value, email)
        }
    }

    override suspend fun updateUserPhoneNumberInDatabase(value: String, email: String) {
        withContext(ioDispatcher) {
            appDatabaseDAO.updateUserPhoneNumber(value, email)
        }
    }

    override suspend fun updateUserProfileAvatarInDatabase(value: Uri, email: String) {
        withContext(ioDispatcher) {
            appDatabaseDAO.updateUserProfileImage(value, email)
        }
    }
}