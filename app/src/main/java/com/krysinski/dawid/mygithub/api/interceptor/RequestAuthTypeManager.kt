package com.krysinski.dawid.mygithub.api.interceptor

import okhttp3.Request

class RequestAuthTypeManager {

    companion object {

        private val REQUEST_AUTH_TYPES = listOf(
                Pair("authorizations", AuthType.CREDENTIALS),
                Pair("user", AuthType.CREDENTIALS)
        )
    }

    fun getType(request: Request): AuthType {
        val url = request.url().encodedPath()
        return REQUEST_AUTH_TYPES
                .firstOrNull { url.contains(it.first, true) }
                ?.second ?: AuthType.ANONYMOUS
    }
}

