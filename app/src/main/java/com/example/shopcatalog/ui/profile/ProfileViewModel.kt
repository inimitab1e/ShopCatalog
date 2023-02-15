package com.example.shopcatalog.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.repository.UsersDatabaseLocalRepository
import com.example.shopcatalog.domain.security.PrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val usersDatabaseLocalRepository: UsersDatabaseLocalRepository,
    private val prefHelper: PrefHelper
) : ViewModel() {

    fun doLogout() {
        prefHelper.clear()
    }

    fun updateUserGender(gender: String) {
        viewModelScope.launch {
            usersDatabaseLocalRepository.updateUserGenderInDatabase(gender, getEmail())
        }
    }

    fun updateUserPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            usersDatabaseLocalRepository.updateUserPhoneNumberInDatabase(phoneNumber, getEmail())
        }
    }

    fun updateUserProfileImage(image: Uri, email: String) {
        viewModelScope.launch {
            usersDatabaseLocalRepository.updateUserProfileAvatarInDatabase(image, getEmail())
        }
    }

    private fun getEmail(): String {
        val userEmail = prefHelper.getUserEmail()
        checkNotNull(userEmail)
        return userEmail
    }
}