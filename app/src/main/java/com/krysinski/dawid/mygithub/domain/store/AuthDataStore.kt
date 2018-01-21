package com.krysinski.dawid.mygithub.domain.store

import com.krysinski.dawid.mygithub.domain.data.AuthToken
import com.krysinski.dawid.mygithub.domain.error.DataDoesNotExistException

interface AuthDataStore {

    fun saveAuthHash(hash: String)

    @Throws(DataDoesNotExistException::class)
    fun loadAuthHash(): String

    fun saveToken(authToken: AuthToken)

    @Throws(DataDoesNotExistException::class)
    fun loadToken(): AuthToken

    fun hasCredentials(): Boolean

    fun clearData()
}