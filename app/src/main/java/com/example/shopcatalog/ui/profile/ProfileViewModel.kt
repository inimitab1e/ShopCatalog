package com.example.shopcatalog.ui.profile

import androidx.lifecycle.ViewModel
import com.example.shopcatalog.domain.security.PrefHelper
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val prefHelper: PrefHelper
) : ViewModel() {

    fun doLogout() {
        prefHelper.clear()
    }
}