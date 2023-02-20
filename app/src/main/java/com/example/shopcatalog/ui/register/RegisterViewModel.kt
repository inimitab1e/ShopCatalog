package com.example.shopcatalog.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.model.authentication.AuthResponse
import com.example.shopcatalog.domain.repository.AuthenticationRepository
import com.example.shopcatalog.domain.repository.UsersDatabaseLocalRepository
import com.example.shopcatalog.domain.security.PrefHelper
import com.example.shopcatalog.domain.utils.network_utils.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val usersDatabaseLocalRepository: UsersDatabaseLocalRepository,
    private val prefHelper: PrefHelper
) : ViewModel() {

    private val _isRegistrationSuccess = MutableStateFlow(false)
    val isRegistrationSuccess: StateFlow<Boolean> get() = _isRegistrationSuccess.asStateFlow()

    private val _errorRegistrationResponseMessage =
        MutableSharedFlow<String?>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val errorRegistrationResponseMessage: SharedFlow<String?> =
        _errorRegistrationResponseMessage.asSharedFlow()

    fun doRegistration(userName: String, email: String, password: String) {
        viewModelScope.launch {
            when (val response = authenticationRepository.doRegistrationRequest(
                userName,
                email,
                password
            )) {
                is Result.Success -> {
                    insertTokenValuesInPreference(response.value, email)
                    initUserDatabaseInfo(email, userName)
                    _isRegistrationSuccess.value = true
                }
                is Result.Failure<*> -> _errorRegistrationResponseMessage.tryEmit(response.error?.message)
            }
        }
    }

    private fun initUserDatabaseInfo(email: String, userName: String) {
        usersDatabaseLocalRepository.insertInitialUserInfoToDatabase(email, userName)
    }

    private fun insertTokenValuesInPreference(authResponse: AuthResponse, email: String) {
        with(prefHelper) {
            clear()
            saveUserInfo(
                accessToken = authResponse.accessToken,
                refreshToken = authResponse.refreshToken,
                email = email
            )
        }
    }
}