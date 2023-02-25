package com.example.shopcatalog.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.repository.UsersDatabaseLocalRepository
import com.example.shopcatalog.domain.security.PrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePhoneNumberViewModel @Inject constructor(
    private val usersDatabaseLocalRepository: UsersDatabaseLocalRepository,
    private val prefHelper: PrefHelper
) : ViewModel() {

    fun updateUserPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            usersDatabaseLocalRepository.updateUserPhoneNumberInDatabase(phoneNumber, getEmail())
        }
    }

    private fun getEmail(): String {
        val userEmail = prefHelper.getUserEmail()
        checkNotNull(userEmail)
        return userEmail
    }
}