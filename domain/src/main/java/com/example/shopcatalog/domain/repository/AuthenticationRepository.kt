package com.example.shopcatalog.domain.repository

import com.example.shopcatalog.domain.model.authentication.AuthResponse
import com.example.shopcatalog.domain.utils.network_utils.result.Result

interface AuthenticationRepository {

    suspend fun doRegistrationRequest(
        userName: String,
        email: String,
        password: String
    ): Result<AuthResponse>

    suspend fun doLoginRequest(
        email: String,
        password: String
    ): Result<AuthResponse>
}