package com.krysinski.dawid.mygithub.ui.dashboard

import com.krysinski.dawid.mygithub.api.RetrofitServiceManager
import com.krysinski.dawid.mygithub.domain.data.UserDetails
import com.krysinski.dawid.mygithub.domain.service.AuthorizationService
import com.krysinski.dawid.mygithub.domain.service.UserService
import com.krysinski.dawid.mygithub.domain.store.AuthDataStore
import com.krysinski.dawid.mygithub.tool.StoreProvider
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DashboardInteractor {

    private val authDataStore: AuthDataStore = StoreProvider.authDataStore
    private val authorizationService: AuthorizationService = RetrofitServiceManager.authorizationService
    private val userService: UserService = RetrofitServiceManager.userService

    fun signOut(): Completable {
        val authToken = authDataStore.loadToken()

        return authorizationService
                .deauthorize(authToken.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun userDetails(): Single<UserDetails> = userService
            .userDetails()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun clearAuthData() {
        authDataStore.clearData()
    }
}