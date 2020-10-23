package com.kt.basedata.entity.login.request

import com.google.gson.annotations.SerializedName

class EmailSignUpRequest {
    @SerializedName("tenant_id")
    var tenantId: String? = null
    @SerializedName("client_id")
    var clientId: String? = null
    var email: String? = null
    var password: String? = null
    @SerializedName("first_name")
    var firstName: String? = null
    @SerializedName("last_name")
    var lastName: String? = null
    var phone: String? = null
}