package com.example.shopcatalog.data

import android.net.Uri
import com.example.shopcatalog.data.model.authenticationDto.AuthResponseDto
import com.example.shopcatalog.data.model.authenticationDto.ValidityResponseDto
import com.example.shopcatalog.domain.local.entities.Users
import com.example.shopcatalog.domain.model.CurrentUserInfo
import com.example.shopcatalog.domain.model.authentication.AuthResponse
import com.example.shopcatalog.domain.model.authentication.ValidityResponse

fun AuthResponseDto.toAuthResponse() : AuthResponse = AuthResponse(
    accessToken = accessToken,
    refreshToken = refreshToken
)

fun ValidityResponseDto.toValidityResponse() : ValidityResponse = ValidityResponse(
    message = message
)

fun Users.toCurrentUserInfo() : CurrentUserInfo = CurrentUserInfo(
    email = email ?: "",
    userName = userName ?: "",
    gender = gender ?: "Пол не указан",
    phoneNumber = phoneNumber ?: "Номер телефона не указан",
    profileImage = profileImage ?: Uri.EMPTY
)