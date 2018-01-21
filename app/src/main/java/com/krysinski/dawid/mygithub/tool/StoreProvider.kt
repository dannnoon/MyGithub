package com.krysinski.dawid.mygithub.tool

import android.app.Application
import com.krysinski.dawid.mygithub.domain.store.AuthDataSharedPrefs
import com.krysinski.dawid.mygithub.domain.store.AuthDataStore

object StoreProvider {

    private lateinit var application: Application

    val authDataStore: AuthDataStore by lazy {
        AuthDataSharedPrefs(application)
    }

    fun init(application: Application) {
        this.application = application
    }
}