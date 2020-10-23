package com.kt.basedata.basedatasoure

import com.kt.basedata.entity.login.request.DeviceResponse
import io.reactivex.Completable
import io.reactivex.Observable

interface IDeviceAuthDataSource {
    fun getDeviceConnectCode(): Observable<DeviceResponse>
    fun saveAccessToken(): Completable
    fun getDeviceAccessTokenFromServer(id: String): Observable<DeviceResponse>
}