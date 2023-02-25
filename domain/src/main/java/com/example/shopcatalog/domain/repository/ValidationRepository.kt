package com.example.shopcatalog.domain.repository

import com.example.shopcatalog.domain.model.authentication.ValidationResult

interface ValidationRepository {

    fun validateEmail(email: String): ValidationResult

    fun validatePassword(password: String): ValidationResult

    fun validateUsername(userName: String): ValidationResult
}