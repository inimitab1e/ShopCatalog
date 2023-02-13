package com.example.shopcatalog.data.repository

import com.example.shopcatalog.data.AppDispatchers
import com.example.shopcatalog.domain.utils.network_utils.result.Result
import com.example.shopcatalog.data.network.AuthService
import com.example.shopcatalog.data.toAuthResponse
import com.example.shopcatalog.data.toValidityResponse
import com.example.shopcatalog.domain.model.authentication.AuthResponse
import com.example.shopcatalog.domain.model.authentication.RefreshRequest
import com.example.shopcatalog.domain.model.authentication.ValidityResponse
import com.example.shopcatalog.domain.repository.LaunchAppRepository
import com.example.shopcatalog.domain.utils.network_utils.result.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LaunchAppRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val appDispatchers: AppDispatchers
) : LaunchAppRepository {

    override suspend fun checkAccessTokenValidity(): Result<ValidityResponse> =
        withContext(appDispatchers.io) {
            val response = authService.checkTokenValidity()
            return@withContext response.map { value -> value.toValidityResponse() }
        }

    override suspend fun refreshUserTokens(refreshRequest: RefreshRequest): Result<AuthResponse> =
        withContext(appDispatchers.io) {
            val response = authService.refreshTokens(refreshRequest)
            return@withContext response.map { value -> value.toAuthResponse() }
        }
}