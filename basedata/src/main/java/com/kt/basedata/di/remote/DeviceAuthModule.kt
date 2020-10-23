package com.kt.basedata.di.remote

import com.kt.basedata.basedatasoure.IDeviceAuthDataSource
import com.kt.basedata.local.CoreSharePreference
import com.kt.basedata.remote.DeviceAuthApi
import com.kt.basedata.remote.auth.DeviceAuthDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
class DeviceAuthModule {

    @Provides
    fun providesDeviceAuth(
        deviceAuthApi: DeviceAuthApi,
        coreSharePreference: CoreSharePreference
    ): IDeviceAuthDataSource {
        return DeviceAuthDataSourceImpl(deviceAuthApi, coreSharePreference)
    }
}