package com.krysinski.dawid.mygithub.tool

import android.util.Base64

object BasicAuthHashFactory {

    fun create(username: String, password: String): String {
        val credentials = Base64.encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP)
        return "Basic $credentials"
    }
}