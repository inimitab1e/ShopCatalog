package com.example.shopcatalog.domain.security

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.shopcatalog.domain.utils.StringConstants

class PrefHelper(context: Context) {

    private val PREFS_NAME = StringConstants.prefsName
    private var sharedPref: SharedPreferences
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val editor: SharedPreferences.Editor

    init {
        sharedPref = EncryptedSharedPreferences.create(
            PREFS_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        editor = sharedPref.edit()
    }

    fun saveUserInfo(accessToken: String?, refreshToken: String?, email: String) {
        editor.putString("AccessToken", accessToken)
            .putString("RefreshToken", refreshToken)
            .putString("Email", email)
            .apply()
    }

    fun deleteKey(key: String) {
        editor.remove(key)
            .apply()
     }

    fun getAccessToken(): String? = sharedPref.getString("AccessToken", null)

    fun getRefreshToken(): String? = sharedPref.getString("RefreshToken", null)

    fun getUserEmail(): String? = sharedPref.getString("Email", null)

    fun clear() {
        editor.remove("AccessToken")
            .remove("RefreshToken")
            .remove("Email")
            .apply()
    }
}