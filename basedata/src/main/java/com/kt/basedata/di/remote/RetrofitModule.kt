package com.kt.basedata.di.remote

import android.content.Context
import com.google.gson.Gson
import com.kt.basedata.Constants
import com.kt.basedata.entity.config.EndPoints
import com.kt.basedata.local.CoreSharePreference
import com.kt.basedata.remote.DeviceAuthApi
import com.kt.basedata.remote.LoginApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tun.kt.apilib.apihistory.CustomInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {

    companion object {
        const val ENVIRONMENT = "environment"
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer"
    }

    @Provides
    @Singleton
    @Named(ENVIRONMENT)
    fun providesEnvironment(coreModule: CoreSharePreference): Int = coreModule.getEnvironment()

    @Provides
    fun provideEndpoint(context: Context, @Named(ENVIRONMENT) environment: Int): EndPoints {
        context.assets.open(Constants.endPoints[environment]!!).apply {
            val json = this.readBytes().toString(Charsets.UTF_8)
            return Gson().fromJson(json, EndPoints::class.java)
        }
    }

    @Singleton
    @Provides
    fun provideInterceptors(authStateManager: CoreSharePreference): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val keyInterceptor = Interceptor { chain ->
            val original = chain.request()

            val newRequest = original.newBuilder()
                .addHeader(
                    AUTHORIZATION,
                    BEARER + authStateManager.getAccessToken()
                )
                .build()

            return@Interceptor chain.proceed(newRequest)
        }
        interceptors.add(keyInterceptor)
        val interceptor = CustomInterceptor()
        interceptor.level = CustomInterceptor.Level.BODY
        interceptors.add(interceptor)
        return interceptors
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptors: ArrayList<Interceptor>): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        if (interceptors.isNotEmpty()) {
            interceptors.forEach {
                clientBuilder.addInterceptor(it)
            }
        }
        val loggingInterceptor = CustomInterceptor()
        loggingInterceptor.level = CustomInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun provideLoginApi(endPoints: EndPoints, builder: Retrofit.Builder): LoginApi {
        return builder.baseUrl(endPoints.deviceAuthEndpoint)
            .build()
            .create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDeviceAuthApi(endPoints: EndPoints, builder: Retrofit.Builder): DeviceAuthApi {
        return builder.baseUrl(endPoints.deviceAuthEndpoint)
            .build()
            .create(DeviceAuthApi::class.java)
    }

}