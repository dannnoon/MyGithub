package com.krysinski.dawid.mygithub.ui.login

import com.krysinski.dawid.mygithub.api.RetrofitServiceManager
import com.krysinski.dawid.mygithub.domain.data.AuthResponse
import com.krysinski.dawid.mygithub.domain.data.AuthToken
import com.krysinski.dawid.mygithub.domain.service.AuthorizationService
import com.krysinski.dawid.mygithub.domain.store.AuthDataStore
import com.krysinski.dawid.mygithub.tool.BasicAuthHashFactory
import com.krysinski.dawid.mygithub.tool.StoreProvider
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginInteractor {

    private val authorizationService: AuthorizationService by lazy {
        RetrofitServiceManager.authorizationService
    }

    private val authDataStore: AuthDataStore = StoreProvider.authDataStore

    fun checkIfLoggedIn(): Boolean = authDataStore.hasCredentials()

    fun createAuthHash(username: String, password: String): String =
            BasicAuthHashFactory.create(username, password)

    fun signIn(): Single<AuthResponse> = authorizationService
            .authorize()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun saveToken(authToken: AuthToken) {
        authDataStore.saveToken(authToken)
    }

    fun saveAuthHash(authHash: String) {
        authDataStore.saveAuthHash(authHash)
    }

    fun clearAuthData() {
        authDataStore.clearData()
    }
}