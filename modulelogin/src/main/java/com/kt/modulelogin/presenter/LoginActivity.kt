package com.kt.modulelogin.presenter

import android.os.Bundle
import android.util.Log
import androidx.annotation.IntRange
import androidx.databinding.DataBindingUtil
import com.kt.basedata.DataState
import com.kt.modulelogin.di.LoginModule
import com.kt.modulelogin.model.DeviceConnectCode
import com.kt.baseusercase.di.UseCaseComponent
import com.kt.modulelogin.R
import com.kt.modulelogin.databinding.ActivityLoginBinding
import com.kt.modulelogin.di.DaggerLoginComponent
import roxwin.tun.baseui.base.BaseActivity
import roxwin.tun.baseui.dialog.ConfirmDialogControl
import roxwin.tun.baseui.utils.setVisible
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: LoginViewModel

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    }

    override fun initDependencies() {
        DaggerLoginComponent.builder()
            .loginModule(LoginModule((this)))
            .useCaseComponent(UseCaseComponent.getUserCaseComponent())
            .build()
            .inject(this)
    }

    override fun initView() {
        binding.viewModel = viewModel
    }

    override fun initAction() {
        binding.btnLogin.setOnClickListener {
            viewModel.changeBtnAddState()
        }
        binding.pro.setOnClickListener {
            reInjectEnv(2)
        }
        binding.dev.setOnClickListener {
            reInjectEnv(0)
        }
        binding.uat.setOnClickListener {
            reInjectEnv(1)
        }
        viewModel.liveDataConnectCode.observe(this, {
            handleDeviceConnectCode(it)
        })
        viewModel.liveDataToken.observe(this, {
            handleAccessToken(it)
        })
    }

    private fun handleAccessToken(dataState: DataState<DeviceConnectCode>) {
        when (dataState) {
            is DataState.Success -> {
                viewModel.disposable.clear()
            }
            is DataState.Loading -> {

            }
            is DataState.Error -> {
                ConfirmDialogControl.Builder(this)
                    .title("Error")
                    .message(dataState.throwable.message)
                    .setPositiveButton(getString(android.R.string.ok))
                    .show()
                viewModel.disposable.clear()
            }
        }
    }

    fun reInjectEnv(@IntRange(from = 0L, to = 2L) env: Int) {
        viewModel.changeEnvironment(env)
        DaggerLoginComponent.builder()
            .loginModule(LoginModule((this)))
            .useCaseComponent(UseCaseComponent.reCreateUserCaseComponent())
            .build()
            .inject(this)
    }

    private fun handleDeviceConnectCode(dataState: DataState<DeviceConnectCode>) {
        when (dataState) {
            is DataState.Success -> {
                Log.e("Device connect code", dataState.value.connectCode.get()!!)
                viewModel.getAccessToken(dataState.value.id.get()!!)
            }
            is DataState.Loading -> {
                binding.progressCircular.setVisible(true)
            }
            is DataState.Error -> {
                ConfirmDialogControl.Builder(this)
                    .title("Error")
                    .setPositiveButton(getString(android.R.string.ok))
                    .message(dataState.throwable.message)
                    .show()
            }
        }
    }

}