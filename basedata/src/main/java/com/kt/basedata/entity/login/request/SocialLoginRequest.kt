package com.kt.basedata.entity.login.request

import com.google.gson.annotations.SerializedName

class SocialLoginRequest {
    var scope: String? = null

    @SerializedName("access_type")
    var accessType: String? = null

    @SerializedName("grant_type")
    var grantType: String? = null

    @SerializedName("client_id")
    var clientId: String? = null
    var username: String? = null
    var password: String? = null

    @SerializedName("social_token")
    var socialToken: String? = null
    var provider: String? = null

    @SerializedName("client_secret")
    var clientSecret: String? = null

    @SerializedName("tenant_id")
    var tenantId: String? = null
}