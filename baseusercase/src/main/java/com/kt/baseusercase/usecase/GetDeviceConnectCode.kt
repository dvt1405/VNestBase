package com.kt.baseusercase.usecase

import com.kt.basedata.basedatasoure.IDeviceAuthDataSource
import com.kt.basedata.entity.login.request.DeviceResponse
import com.kt.baseusercase.UserCase
import javax.inject.Inject

class GetDeviceConnectCode @Inject constructor(private val deviceDataSourceDataSource: IDeviceAuthDataSource) : UserCase<DeviceResponse>() {
    operator fun invoke() =deviceDataSourceDataSource.getDeviceConnectCode()
}