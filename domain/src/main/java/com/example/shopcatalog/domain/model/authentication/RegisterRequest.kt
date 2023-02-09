package com.example.shopcatalog.domain.model.authentication

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("username")
    val userName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
