package com.kt.basedata.entity.login.response

import com.google.gson.annotations.SerializedName

class AuthResponse {
    @SerializedName("access_token")
    private val accessToken: String? = null

    @SerializedName("refresh_token")
    private val refreshToken: String? = null

    @SerializedName("id_token")
    private val idToken: String? = null

    @SerializedName("token_type")
    private val tokenType: String? = null

    private val isTemUser = false
}