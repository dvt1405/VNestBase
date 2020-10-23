package roxwin.tun.baseui.base

import androidx.multidex.MultiDexApplication

abstract class BaseAltitudeApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }

    abstract fun initDependencies()
}