package com.example.shopcatalog.domain.model.authentication

import com.google.gson.annotations.SerializedName

data class RefreshRequest(
    @SerializedName("email")
    val email: String
)