package com.example.shopcatalog.data.network

import com.example.shopcatalog.domain.model.authentication.*
import com.example.shopcatalog.domain.utils.network_utils.result.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Result<AuthResponse>

    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Result<AuthResponse>

    @GET("/validity")
    suspend fun checkTokenValidity() : Result<ValidityResponse>

    @POST("/refresh")
    suspend fun refreshTokens(@Body refreshRequest: RefreshRequest): Result<AuthResponse>
}