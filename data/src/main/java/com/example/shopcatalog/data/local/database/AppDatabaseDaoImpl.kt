package com.example.shopcatalog.data.local.database

import com.example.shopcatalog.data.AppDispatchers
import com.example.shopcatalog.domain.local.AppDatabaseDAO
import com.example.shopcatalog.domain.local.entities.Users
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDatabaseDaoImpl @Inject constructor(
    private val appDatabaseDAO: AppDatabaseDAO,
    private val appDispatchers: AppDispatchers
) {

    suspend fun importUserInfo(userInfo: Users) {
        withContext(appDispatchers.io) {
            appDatabaseDAO.importUserInfo(userInfo = userInfo)
        }
    }
}