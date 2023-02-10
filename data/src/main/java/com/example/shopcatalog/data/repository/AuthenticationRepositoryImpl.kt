package com.example.shopcatalog.data.repository

import com.example.shopcatalog.data.AppDispatchers
import com.example.shopcatalog.data.network.AuthService
import com.example.shopcatalog.domain.model.authentication.AuthResponse
import com.example.shopcatalog.domain.model.authentication.LoginRequest
import com.example.shopcatalog.domain.model.authentication.RegistrationRequest
import com.example.shopcatalog.domain.repository.AuthenticationRepository
import com.example.shopcatalog.domain.utils.network_utils.result.Result
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val appDispatchers: AppDispatchers
) : AuthenticationRepository {

    override suspend fun doRegistrationRequest(
        userName: String,
        email: String,
        password: String
    ): Result<AuthResponse> = withContext(appDispatchers.io) {
        authService.register(RegistrationRequest(userName, email, password))
    }

    override suspend fun doLoginRequest(email: String, password: String): Result<AuthResponse> =
        withContext(appDispatchers.io) {
            authService.login(LoginRequest(email, password))
        }
}