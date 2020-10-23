package com.kt.basedata.di

import android.content.Context
import com.kt.basedata.App
import com.kt.basedata.basedatasoure.IDeviceAuthDataSource
import com.kt.basedata.di.remote.DeviceAuthModule
import com.kt.basedata.di.remote.RetrofitModule
import com.kt.basedata.local.CoreSharePreference
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        (CoreModule::class),
        (RetrofitModule::class),
        (DeviceAuthModule::class)
    ]
)
@Singleton
interface CoreComponent {
    fun inject(app: App)
    fun context(): Context
    fun coreSharePref(): CoreSharePreference
    fun deviceAuthApi(): IDeviceAuthDataSource
}