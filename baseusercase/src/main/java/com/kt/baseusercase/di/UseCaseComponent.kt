package com.kt.baseusercase.di

import com.kt.basedata.App
import com.kt.basedata.di.CoreComponent
import com.kt.baseusercase.usecase.ChangeEnvironment
import com.kt.baseusercase.usecase.GetAccessToken
import com.kt.baseusercase.usecase.GetDeviceConnectCode
import dagger.Component

@Component(
    modules = [
        (UseCaseModule::class)
    ],
    dependencies = [(CoreComponent::class)]
)
@UserCaseScope
interface UseCaseComponent {
    fun getAccessToken(): GetAccessToken
    fun getConnectCode(): GetDeviceConnectCode
    fun changeEnv(): ChangeEnvironment

    companion object {
        fun getUserCaseComponent(): UseCaseComponent = DaggerUseCaseComponent.builder()
            .coreComponent(App.get().coreComponent)
            .useCaseModule(UseCaseModule())
            .build()

        fun reCreateUserCaseComponent(): UseCaseComponent = DaggerUseCaseComponent.builder()
            .coreComponent(App.get().reInject())
            .useCaseModule(UseCaseModule())
            .build()
    }
}