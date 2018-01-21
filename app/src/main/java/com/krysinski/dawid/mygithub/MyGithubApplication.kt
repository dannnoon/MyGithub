package com.krysinski.dawid.mygithub

import android.app.Application
import com.krysinski.dawid.mygithub.tool.StoreProvider

class MyGithubApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        StoreProvider.init(this)
    }
}