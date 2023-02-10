package com.example.shopcatalog.domain.model.authentication

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("username")
    val userName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
