package com.example.shopcatalog.domain.model

import android.net.Uri

data class CurrentUserInfo(
    val email: String,
    val userName: String,
    val gender: String,
    val phoneNumber: String,
    val profileImage: Uri
)
