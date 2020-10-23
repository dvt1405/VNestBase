package com.kt.basedata.local

import android.content.SharedPreferences
import android.os.Environment
import androidx.annotation.IntRange
import javax.inject.Inject

class CoreSharePreference @Inject constructor(val sharedPreferences: SharedPreferences) {

    companion object {
        const val DEV = 0
        const val UAT = 1
        const val PRO = 2

        private const val ENV_KEY = "extra:environment"
        private const val ACCESS_TOKEN_KEY = "extra:access_token"
    }

    @IntRange(from = 0L, to = 2L)
    fun getEnvironment(): Int = sharedPreferences.getInt(ENV_KEY, 0)

    fun saveEnvironment(@IntRange(from = 0L, to = 2L) environment: Int) {
        sharedPreferences.edit()
            .putInt(ENV_KEY, environment)
            .apply()
    }

    fun getAccessToken(): String? = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    fun saveAccessToken(accessToken: String) = sharedPreferences.edit()
        .putString(ACCESS_TOKEN_KEY, accessToken)
        .apply()

    fun clear() {
        sharedPreferences.edit()
            .clear()
            .apply()
    }
}