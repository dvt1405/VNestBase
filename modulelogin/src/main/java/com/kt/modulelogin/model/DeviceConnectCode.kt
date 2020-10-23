package com.kt.modulelogin.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.kt.basedata.entity.login.request.DeviceResponse

data class DeviceConnectCode(
    @Bindable
    var id: ObservableField<String> = ObservableField(),
    @Bindable
    var connectCode: ObservableField<String> = ObservableField("_ _ _ _"),
    var status: String? = null,
    var connectCodeState: ObservableField<String> = ObservableField("Connect"),
    @Bindable
    var loadingState: ObservableField<Boolean> = ObservableField(false)
) : BaseObservable() {
    fun mapFrom(deviceResponse: DeviceResponse) {
        id.set(deviceResponse.id)
        connectCode.set(deviceResponse.connectCode)
        status = deviceResponse.status
    }
}