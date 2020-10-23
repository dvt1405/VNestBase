package com.kt.modulelogin.di

import com.kt.modulelogin.presenter.LoginActivity
import com.kt.baseusercase.di.UseCaseComponent
import dagger.Component

@Component(
    modules = [(LoginModule::class)],
    dependencies = [(UseCaseComponent::class)]
)
@LoginActivityScope
interface LoginComponent {
    fun inject(activity: LoginActivity)
}