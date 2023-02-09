package com.example.shopcatalog.data.network

import com.example.shopcatalog.domain.model.authentication.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<AuthResponse>

    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @GET("/validity")
    suspend fun checkAccessTokenValidity() : Response<ValidityResponse>

    @POST("/refresh")
    suspend fun refreshTokens(@Body refreshRequest: RefreshRequest): Response<AuthResponse>
}