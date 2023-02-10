package com.example.shopcatalog.data.repository

import com.example.shopcatalog.data.AppDispatchers
import com.example.shopcatalog.domain.utils.network_utils.result.Result
import com.example.shopcatalog.data.network.AuthService
import com.example.shopcatalog.domain.model.authentication.AuthResponse
import com.example.shopcatalog.domain.model.authentication.RefreshRequest
import com.example.shopcatalog.domain.model.authentication.ValidityResponse
import com.example.shopcatalog.domain.repository.LaunchAppRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LaunchAppRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val appDispatchers: AppDispatchers
) : LaunchAppRepository {

    override suspend fun checkAccessTokenValidity(): Result<ValidityResponse> =
        withContext(appDispatchers.io) {
            authService.checkTokenValidity()
        }

    override suspend fun refreshUserTokens(refreshRequest: RefreshRequest): Result<AuthResponse> =
        withContext(appDispatchers.io) {
            authService.refreshTokens(refreshRequest)
        }
}