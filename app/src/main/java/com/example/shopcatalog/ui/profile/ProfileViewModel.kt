package com.example.shopcatalog.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.repository.UsersDatabaseLocalRepository
import com.example.shopcatalog.domain.security.PrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val usersDatabaseLocalRepository: UsersDatabaseLocalRepository,
    private val prefHelper: PrefHelper
) : ViewModel() {

    fun doLogout() {
        prefHelper.clear()
    }

    fun updateUserGender(gender: String, email: String) {
        viewModelScope.launch {
            usersDatabaseLocalRepository.updateUserGenderInDatabase(gender, email)
        }
    }

    fun updateUserPhoneNumber(phoneNumber: String, email: String) {
        viewModelScope.launch {
            usersDatabaseLocalRepository.updateUserPhoneNumberInDatabase(phoneNumber, email)
        }
    }

    fun updateUserProfileImage(image: Uri, email: String) {
        viewModelScope.launch {
            usersDatabaseLocalRepository.updateUserProfileAvatarInDatabase(image, email)
        }
    }
}