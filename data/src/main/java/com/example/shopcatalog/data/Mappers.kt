package com.example.shopcatalog.data

import com.example.shopcatalog.data.model.authenticationDto.AuthResponseDto
import com.example.shopcatalog.data.model.authenticationDto.ValidityResponseDto
import com.example.shopcatalog.domain.model.authentication.AuthResponse
import com.example.shopcatalog.domain.model.authentication.ValidityResponse

fun AuthResponseDto.toAuthResponse() : AuthResponse = AuthResponse(
    accessToken = accessToken,
    refreshToken = refreshToken
)

fun ValidityResponseDto.toValidityResponse() : ValidityResponse = ValidityResponse(
    message = message
)