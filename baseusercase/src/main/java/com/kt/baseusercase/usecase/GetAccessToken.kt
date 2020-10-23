package com.kt.baseusercase.usecase

import com.kt.basedata.basedatasoure.IDeviceAuthDataSource
import com.kt.basedata.entity.login.request.DeviceResponse
import com.kt.baseusercase.UserCase
import javax.inject.Inject

class GetAccessToken @Inject constructor(private val deviceAuthDataSource: IDeviceAuthDataSource) :
    UserCase<DeviceResponse>() {
    operator fun invoke(deviceId: String) =
        deviceAuthDataSource.getDeviceAccessTokenFromServer(deviceId)
}