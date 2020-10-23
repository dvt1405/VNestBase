package com.kt.basedata

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kt.basedata.di.CoreComponent
import com.kt.basedata.di.CoreModule
import com.kt.basedata.di.DaggerCoreComponent
import com.kt.basedata.di.remote.DeviceAuthModule
import com.kt.basedata.di.remote.RetrofitModule

class App : Application() {
    private val _coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.builder()
            .coreModule(CoreModule(applicationContext))
            .retrofitModule(RetrofitModule())
            .deviceAuthModule(DeviceAuthModule())
            .build()
    }
    val coreComponent: CoreComponent
        get() = _coreComponent

    fun reInject() = DaggerCoreComponent.builder()
            .coreModule(CoreModule(applicationContext))
            .retrofitModule(RetrofitModule())
            .deviceAuthModule(DeviceAuthModule())
            .build()


    override fun onCreate() {
        super.onCreate()
        app = this
        FirebaseApp.initializeApp(this)
    }


    companion object {
        private lateinit var app: App
        fun get(): App = app
    }
}