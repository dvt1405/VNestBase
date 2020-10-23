package com.kt.modulelogin.presenter

import com.kt.baseusercase.usecase.ChangeEnvironment
import com.kt.baseusercase.usecase.GetAccessToken
import com.kt.baseusercase.usecase.GetDeviceConnectCode
import javax.inject.Inject

data class LoginInteractors @Inject constructor(
    val getDeviceConnectCode: GetDeviceConnectCode,
    val getAccessToken: GetAccessToken,
    val changeEnvironment: ChangeEnvironment
)