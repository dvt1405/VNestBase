package com.kt.modulelogin.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.kt.modulelogin.presenter.LoginViewModel
import com.kt.modulelogin.presenter.LoginViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LoginModule(private val lifeCycleScope: ViewModelStoreOwner) {

    @LoginActivityScope
    @Provides
    fun providesViewModelStoreOwner(): ViewModelStoreOwner = lifeCycleScope

    @LoginActivityScope
    @Provides
    fun providesMainViewModel(factory: LoginViewModelFactory, lifecycleOwner: ViewModelStoreOwner) =
        ViewModelProvider(lifecycleOwner, factory).get(LoginViewModel::class.java)

}