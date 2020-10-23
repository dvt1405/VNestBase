package com.kt.basedata.remote.auth

import com.google.firebase.iid.FirebaseInstanceId
import com.kt.basedata.Transformer
import com.kt.basedata.basedatasoure.IDeviceAuthDataSource
import com.kt.basedata.entity.login.request.DeviceRequest
import com.kt.basedata.entity.login.request.DeviceResponse
import com.kt.basedata.local.CoreSharePreference
import com.kt.basedata.remote.DeviceAuthApi
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DeviceAuthDataSourceImpl @Inject constructor(
    val deviceAuthApi: DeviceAuthApi,
    val coreSharePreference: CoreSharePreference
) : IDeviceAuthDataSource {
    override fun getDeviceConnectCode(): Observable<DeviceResponse> {
        return deviceAuthApi.postDevice(DeviceRequest(FirebaseInstanceId.getInstance().id))
            .compose(Transformer<DeviceResponse>())
    }


    override fun saveAccessToken(): Completable {
        return Completable.complete()
    }

    override fun getDeviceAccessTokenFromServer(id: String): Observable<DeviceResponse> {
        return deviceAuthApi.getDeviceAccessToken(id)
            .compose(Transformer<DeviceResponse>())
            .repeatWhen {
                it.delay(1, TimeUnit.SECONDS)
            }
    }
}