package com.example.shopcatalog.ui.autentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.model.authentication.AuthResponse
import com.example.shopcatalog.domain.repository.AuthenticationRepository
import com.example.shopcatalog.domain.repository.ValidationRepository
import com.example.shopcatalog.domain.security.PrefHelper
import com.example.shopcatalog.domain.utils.network_utils.result.Result
import com.example.shopcatalog.ui.autentication.AuthenticationFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val validationRepository: ValidationRepository,
    private val prefHelper: PrefHelper
) : ViewModel() {

    private val _isLoginSuccess = MutableStateFlow(false)
    val isLoginSuccess: StateFlow<Boolean> get() = _isLoginSuccess.asStateFlow()

    private val _errorValidationFormsMessage =
        MutableSharedFlow<AuthenticationFormState>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val errorValidationFormsMessage: SharedFlow<AuthenticationFormState> =
        _errorValidationFormsMessage.asSharedFlow()

    private val _errorLoginResponseMessage =
        MutableSharedFlow<String?>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val errorLoginResponseMessage: SharedFlow<String?> =
        _errorLoginResponseMessage.asSharedFlow()

    fun validateAndLogin(email: String, password: String) {
        val passwordResult = validationRepository.validatePassword(password)
        val emailResult = validationRepository.validateUsername(email)

        val hasError = listOf(
            passwordResult,
            emailResult
        ).any { !it.successful }

        if (hasError) {
            _errorValidationFormsMessage.tryEmit(AuthenticationFormState(
                passwordError = passwordResult.errorMessage,
                emailError = emailResult.errorMessage
            ))
        } else {
            doLogin(email, password)
        }
    }

    private fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            when(val response = authenticationRepository.doLoginRequest(email, password)) {
                is Result.Success -> {
                    updateTokenValues(response.value, email)
                    _isLoginSuccess.value = true
                }
                is Result.Failure<*> -> _errorLoginResponseMessage.tryEmit(response.error?.message)
            }
        }
    }

    private fun updateTokenValues(newValues: AuthResponse, userEmail: String) {
        with(prefHelper) {
            clear()
            saveUserInfo(
                email = userEmail,
                accessToken = newValues.accessToken,
                refreshToken = newValues.refreshToken
            )
        }
    }
}