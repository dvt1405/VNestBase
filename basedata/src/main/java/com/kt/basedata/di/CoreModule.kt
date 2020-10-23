package com.kt.basedata.di

import android.content.Context
import android.content.SharedPreferences
import com.kt.basedata.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule(private val context: Context) {

    @Provides
    @Singleton
    fun providesApplicationContext() = context

    @Provides
    @Singleton
    fun providesSharePref(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE)
    }
}