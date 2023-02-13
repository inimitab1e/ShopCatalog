package com.example.shopcatalog.domain.repository

import com.example.shopcatalog.domain.model.authentication.AuthResponse
import com.example.shopcatalog.domain.utils.network_utils.result.Result
import com.example.shopcatalog.domain.model.authentication.RefreshRequest
import com.example.shopcatalog.domain.model.authentication.ValidityResponse

interface LaunchAppRepository {

    suspend fun checkAccessTokenValidity() : Result<ValidityResponse>

    suspend fun refreshUserTokens(refreshRequest: RefreshRequest) : Result<AuthResponse>
}