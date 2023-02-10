package com.example.shopcatalog.ui.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.model.authentication.AuthResponse
import com.example.shopcatalog.domain.model.authentication.RefreshRequest
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

    private val _authErrorResponseMessage =
        MutableSharedFlow<String?>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val authErrorResponseMessage: SharedFlow<String?> =
        _authErrorResponseMessage.asSharedFlow()

    private val _authState =
        MutableSharedFlow<String?>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val authState: SharedFlow<String?> =
        _authState.asSharedFlow()

    init {
        setupInitialUserInfo()
    }

    private fun setupInitialUserInfo() {
        if (prefHelper.getUserEmail() == null) {
            _authState.tryEmit(StringConstants.userNotExists)
        } else {
            handleUserTokensLogic()
        }
    }

    private fun handleUserTokensLogic() {
        viewModelScope.launch {
            when (val isTokenValidResponse = launchAppRepository.checkAccessTokenValidity()) {
                is Result.Success -> {
                    _authState.tryEmit(StringConstants.tokenIsValid)
                }
                is Result.Failure<*> -> {
                    tryToRefreshUserTokens()
                    _authErrorResponseMessage.tryEmit(isTokenValidResponse.error?.message)
                }
            }
        }
    }

    private fun tryToRefreshUserTokens() {
        viewModelScope.launch {
            prefHelper.deleteKey(key = StringConstants.accessTokenTitle)
            val userEmail = prefHelper.getUserEmail()
            checkNotNull(userEmail)
            val refreshResponse =
                launchAppRepository.refreshUserTokens(RefreshRequest(email = userEmail))
            when (refreshResponse) {
                is Result.Success -> {
                    updateTokenValues(refreshResponse.value, userEmail)
                    _authState.tryEmit(StringConstants.refreshSuccess)
                }
                is Result.Failure<*> -> _authErrorResponseMessage.tryEmit(refreshResponse.error?.message)
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