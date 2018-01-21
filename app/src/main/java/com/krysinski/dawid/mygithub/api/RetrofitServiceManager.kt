package com.krysinski.dawid.mygithub.api

import com.krysinski.dawid.mygithub.api.interceptor.AuthInterceptor
import com.krysinski.dawid.mygithub.api.service.authorization.AuthorizationApi
import com.krysinski.dawid.mygithub.api.service.authorization.AuthorizationCloudService
import com.krysinski.dawid.mygithub.api.service.user.UserApi
import com.krysinski.dawid.mygithub.api.service.user.UserCloudService
import com.krysinski.dawid.mygithub.domain.service.AuthorizationService
import com.krysinski.dawid.mygithub.domain.service.UserService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitServiceManager {

    private const val CONNECTION_TIMEOUT = 10L
    private const val BASE_URL = "https://api.github.com/"

    private val RETROFIT by lazy { initRetrofit() }

    private fun initRetrofit(): Retrofit = Retrofit.Builder()
            .client(createOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    private fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .addInterceptor(AuthInterceptor())
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .build()

    private fun createLoggingInterceptor(): Interceptor = HttpLoggingInterceptor()
            .also { it.level = HttpLoggingInterceptor.Level.BODY }

    private val authenticationApi by lazy { RETROFIT.create(AuthorizationApi::class.java) }
    private val userApi by lazy { RETROFIT.create(UserApi::class.java) }

    val authorizationService: AuthorizationService by lazy {
        AuthorizationCloudService(authenticationApi)
    }

    val userService: UserService by lazy {
        UserCloudService(userApi)
    }
}