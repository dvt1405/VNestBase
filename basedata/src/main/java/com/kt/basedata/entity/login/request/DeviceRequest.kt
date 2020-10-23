package com.kt.basedata.entity.login.request

import android.os.Build

class DeviceRequest(var uniqueDeviceId: String) {
    var device: String = Build.DEVICE
    var os: String = "Android " + Build.VERSION.SDK_INT
    var systemId: String
    var permissions: Array<String>
    var subscribedTo: Array<String>

    init {
        this.systemId = DEVICE_SYSTEM_ID
        this.permissions = arrayOf(
            CREATE_GUEST_ARRIVALS,
            KIOSK_MODE,
            READ_GUEST_ARRIVALS,
            SIGN_GUEST_ARRIVALS
        )
        this.subscribedTo = arrayOf("data1", "data2")
    }

    companion object {
        const val READ_GUEST_ARRIVALS = "guesta:r"
        const val CREATE_GUEST_ARRIVALS = "guesta:c"
        const val SIGN_GUEST_ARRIVALS = "guesta:s"
        const val KIOSK_MODE = "guesta:k"
        const val DEVICE_SYSTEM_ID = "displayApp"
    }
}