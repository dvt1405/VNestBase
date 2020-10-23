package com.kt.basedata.entity.login.request

class RefreshTokenRequest {
    var scope: String? = null
    var grant_type: String? = null
    var client_id: String? = null
    var client_secret: String? = null
    var tenant_id: String? = null
    var access_type: String? = null
    var refresh_token: String? = null
}