package com.kt.basedata.remote.auth

import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class Authenticator() : okhttp3.Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val token = "" ?: return null

        return response.request.newBuilder()
            .header(
                "Authorization",
                "Bearer $token"
            )
            .build()
    }
}