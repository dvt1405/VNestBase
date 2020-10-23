package com.kt.basedata.entity.login.request

import com.google.gson.annotations.SerializedName

class LoginRequest {
    private val scope: String? = null
    @SerializedName("access_type")
    private val accessType: String? = null
    @SerializedName("grant_type")
    private val grantType: String? = null
    @SerializedName("client_id")
    private val clientId: String? = null
    private val username: String? = null
    private val password: String? = null
    @SerializedName("client_secret")
    private val clientSecret: String? = null
    @SerializedName("tenant_id")
    private val tenantId: String? = null
}