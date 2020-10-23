package roxwin.tun.baseui.base

import android.os.Bundle
import tun.kt.apilib.apihistory.BaseActivity

abstract class BaseActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDependencies()
    }

    override fun onStart() {
        super.onStart()
        initView()
        initAction()
    }

    protected abstract fun initDependencies()
    protected abstract fun initView()
    protected abstract fun initAction()
}