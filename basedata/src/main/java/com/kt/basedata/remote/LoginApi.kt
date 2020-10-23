package com.kt.basedata.remote

import com.kt.basedata.entity.login.request.EmailSignUpRequest
import com.kt.basedata.entity.login.request.SocialLoginRequest
import com.kt.basedata.entity.login.request.LoginRequest
import com.kt.basedata.entity.login.request.RefreshTokenRequest
import com.kt.basedata.entity.login.response.AuthResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("api/users/signup")
    fun signUp(@Body request: EmailSignUpRequest): Observable<Any>

    @POST("oauth/token")
    fun login(@Body request: LoginRequest): Observable<AuthResponse>

    @POST("oauth/token")
    fun loginGoogle(@Body request: SocialLoginRequest): Observable<AuthResponse>

    @POST("oauth/token")
    fun loginFacebook(@Body request: SocialLoginRequest): Observable<AuthResponse>

    @POST("oauth/token")
    fun refreshToken(@Body request: RefreshTokenRequest): Observable<AuthResponse>
}