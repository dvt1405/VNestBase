package com.kt.basedata

object Constants {
    const val REGISTER_DEVICE_PATH = "devices/"
    const val DEVICE_REFRESH_PATH = "devices/refresh"
    const val DEVICE_LOGOUT_PATH = "devices/logout"
    const val REGISTRATION_CARD_PATH = "express-app/"
    const val GUEST_ARRIVAL_PATH = "guest-arrivals/details"
    const val GUEST_DEPARTURE_PATH = "guest-departures/details"
    const val PROPERTY_PUBLIC_PATH = "properties/public?"
    const val PROPERTY_PRIVATE_PATH = "properties/"
    const val WEATHER_PATH = "weather"
    const val REF_COUNTRY_PATH = "countries"

    private const val CONFIG_DEV_FILENAME = "configDev.json"
    private const val CONFIG_UAT_FILENAME = "configUat.json"
    private const val CONFIG_PRO_FILENAME = "configPro.json"
    const val SHARE_PREF_NAME = "base_share_pref"


    val endPoints by lazy {
        mapOf<Int, String>(
            0 to CONFIG_DEV_FILENAME,
            1 to CONFIG_UAT_FILENAME,
            2 to CONFIG_PRO_FILENAME
        )
    }
}