package com.example.shopcatalog.domain.local.entities

import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Users(
    @PrimaryKey(autoGenerate = true) var ID: Int? = null,
    var email: String?,
    var userName: String?,
    var gender: String?,
    var phoneNumber: String?,
    var profileImage: Uri?
)
