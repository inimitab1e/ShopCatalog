package com.example.shopcatalog.domain.model.authentication

import com.google.gson.annotations.SerializedName

data class ValidityResponse(
    @SerializedName("message")
    val message: String
)