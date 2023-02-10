package com.example.shopcatalog.ui.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.model.authentication.AuthResponse
import com.example.shopcatalog.domain.model.authentication.RefreshRequest
import com.example.shopcatalog.domain.model.authentication.ValidityResponse
import com.example.shopcatalog.domain.utils.network_utils.result.Result
import com.example.shopcatalog.domain.repository.LaunchAppRepository
import com.example.shopcatalog.domain.security.PrefHelper
import com.example.shopcatalog.domain.utils.StringConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchAppViewModel @Inject constructor(
    private val launchAppRepository: LaunchAppRepository,
    private val prefHelper: PrefHelper
) : ViewModel() {

    private val _isUserExist = MutableStateFlow(false)
    val isUserExist: StateFlow<Boolean> get() = _isUserExist.asStateFlow()

    private val _isAccessTokenValid = MutableStateFlow(false)
    val isAccessTokenValid: StateFlow<Boolean> get() = _isAccessTokenValid.asStateFlow()

    private val _isRefreshSuccess = MutableStateFlow(false)
    val isRefreshSuccess: StateFlow<Boolean> get() = _isRefreshSuccess.asStateFlow()

    private val _errorAccessValidityResponseMessage =
        MutableSharedFlow<String?>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val errorAccessValidityResponseMessage: SharedFlow<String?> =
        _errorAccessValidityResponseMessage.asSharedFlow()

    private val _errorRefreshResponseMessage =
        MutableSharedFlow<String?>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val errorRefreshResponseMessage: SharedFlow<String?> =
        _errorRefreshResponseMessage.asSharedFlow()

    init {
        setupInitialUserInfo()
    }

    private fun setupInitialUserInfo() {
        if (prefHelper.getUserEmail() != null) {
            _isUserExist.value = true
        } else {
            handleUserTokensLogic()
        }
    }

    private fun handleUserTokensLogic() {
        viewModelScope.launch {
            when (val isTokenValidResponse = launchAppRepository.checkAccessTokenValidity()) {
                is Result.Success -> _isAccessTokenValid.value =
                    isTokenValidResponse.value.message == StringConstants.tokenIsValid
                is Result.Failure<*> ->
                    _errorAccessValidityResponseMessage.tryEmit(isTokenValidResponse.error.toString())
            }
        }
    }

    fun tryToRefreshUserTokens() {
        viewModelScope.launch {
            val userEmail = prefHelper.getUserEmail()
            checkNotNull(userEmail)
            val refreshResponse =
                launchAppRepository.refreshUserTokens(RefreshRequest(email = userEmail))
            when (refreshResponse) {
                is Result.Success -> {
                    updateTokenValues(refreshResponse.value, userEmail)
                    _isRefreshSuccess.value = true
                }
                is Result.Failure<*> -> _errorRefreshResponseMessage.tryEmit(refreshResponse.error.toString())
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