package com.kt.basedata.remote

import com.kt.basedata.Constants
import com.kt.basedata.entity.login.request.DeviceRequest
import com.kt.basedata.entity.login.request.DeviceResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DeviceAuthApi {
    @POST(Constants.REGISTER_DEVICE_PATH)
    fun postDevice(@Body device: DeviceRequest): Observable<DeviceResponse>

    @GET("${Constants.REGISTER_DEVICE_PATH}/{id}")
    fun getDeviceAccessToken(@Path("id") id: String): Observable<DeviceResponse>



}