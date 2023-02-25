package com.example.shopcatalog.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.model.CurrentUserInfo
import com.example.shopcatalog.domain.repository.UsersDatabaseLocalRepository
import com.example.shopcatalog.domain.security.PrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val usersDatabaseLocalRepository: UsersDatabaseLocalRepository,
    private val prefHelper: PrefHelper
) : ViewModel() {

    private val _currentLocalUserInfo =
        MutableSharedFlow<CurrentUserInfo>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val currentLocalUserInfo: SharedFlow<CurrentUserInfo> =
        _currentLocalUserInfo.asSharedFlow()

    init {
        getCurrentLocalUserInfo()
    }

    fun doLogout() {
        prefHelper.clear()
    }

    private fun getCurrentLocalUserInfo() {
        viewModelScope.launch {
            usersDatabaseLocalRepository.fetchAllUserInfo(getEmail()).collect { userInfo ->
                _currentLocalUserInfo.tryEmit(userInfo)
            }
        }
    }

    fun updateUserGender(gender: String) {
        viewModelScope.launch {
            usersDatabaseLocalRepository.updateUserGenderInDatabase(gender, getEmail())
        }
    }

    fun updateUserProfileImage(image: Uri) {
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