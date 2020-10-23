package com.kt.modulelogin.presenter

import android.util.Log
import androidx.annotation.IntRange
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kt.basedata.DataState
import com.kt.modulelogin.model.DeviceConnectCode
import io.reactivex.schedulers.Schedulers
import roxwin.tun.baseui.base.BaseViewModel

class LoginViewModel(private val interactors: LoginInteractors) : BaseViewModel() {
    val connectCode by lazy { DeviceConnectCode() }
    private val _liveDataConnectCode by lazy { MutableLiveData<DataState<DeviceConnectCode>>() }
    private val _liveDataToken by lazy { MutableLiveData<DataState<DeviceConnectCode>>() }
    val liveDataConnectCode: LiveData<DataState<DeviceConnectCode>>
        get() = _liveDataConnectCode
    val liveDataToken: LiveData<DataState<DeviceConnectCode>>
        get() = _liveDataToken

    private fun getDeviceConnectCode() {
        _liveDataConnectCode.postValue(DataState.Loading(null))
        disposable.add(
            interactors.getDeviceConnectCode()
                .map {
                    connectCode.apply {
                        mapFrom(it)
                    }
                }
                .observeOn(Schedulers.io())
                .subscribe({
                    _liveDataConnectCode.postValue(DataState.Success(it))
                }, {
                    _liveDataConnectCode.postValue(DataState.Error(it))
                })
        )
    }

    fun getAccessToken(id: String) {
        disposable.add(
            interactors.getAccessToken(id)
                .map {
                    connectCode.status = it.status
                    connectCode
                }
                .observeOn(Schedulers.io())
                .subscribe({
                    if (it.status == "pending") {
                        _liveDataToken.postValue(DataState.Loading(it))
                    } else if (it.status == "active") {
                        _liveDataToken.postValue(DataState.Success(it))
                    }
                }, {
                    _liveDataToken.postValue(DataState.Error(it))
                })
        )
    }

    fun changeBtnAddState() {
        if (connectCode.connectCodeState.get() == null) connectCode.connectCodeState.set("Connect")
        val currentState = connectCode.connectCodeState.get()
        if (currentState == "Connect") {
            connectCode.connectCodeState.set("Cancel")
            Log.e("Connect", "true")
            connectCode.loadingState.set(true)
            getDeviceConnectCode()
        } else {
            connectCode.connectCodeState.set("Connect")
            connectCode.loadingState.set(false)
            connectCode.connectCode.set("_ _ _ _")
            disposable.clear()
        }
    }

    fun changeEnvironment(@IntRange(from = 0L, to = 2L) env: Int) {
        interactors.changeEnvironment(env)
    }

}