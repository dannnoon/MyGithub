package com.krysinski.dawid.mygithub.api.interceptor

import com.krysinski.dawid.mygithub.domain.store.AuthDataStore
import com.krysinski.dawid.mygithub.tool.StoreProvider
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    private val authDataStore: AuthDataStore = StoreProvider.authDataStore
    private val requestAuthTypeManager = RequestAuthTypeManager()

    override fun intercept(chain: Interceptor.Chain): Response {
        return when (requestAuthTypeManager.getType(chain.request())) {
            AuthType.CREDENTIALS -> addCredentialHeaders(chain)
            else -> proceedWithoutChanges(chain)
        }
    }

    private fun addCredentialHeaders(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        builder.addHeader("Authorization", authDataStore.loadAuthHash())

        return chain.proceed(builder.build())
    }

    private fun proceedWithoutChanges(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().newBuilder().build())
    }
}