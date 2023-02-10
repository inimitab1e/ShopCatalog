package com.example.shopcatalog.ui.profile

import androidx.lifecycle.ViewModel
import com.example.shopcatalog.domain.security.PrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val prefHelper: PrefHelper
) : ViewModel() {

    fun doLogout() {
        prefHelper.clear()
    }
}