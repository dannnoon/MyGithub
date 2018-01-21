package com.krysinski.dawid.mygithub.domain.store

import android.content.Context
import android.content.SharedPreferences
import com.krysinski.dawid.mygithub.domain.data.AuthToken
import com.krysinski.dawid.mygithub.domain.error.DataDoesNotExistException

class AuthDataSharedPrefs(context: Context) : AuthDataStore {

    companion object {

        private const val AUTH_TOKEN_SHARED_PREFS = "auth_token_prefs"
        private const val AUTH_HASH = "auth_hash"
        private const val TOKEN = "token"
        private const val ID = "id"
    }

    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(AUTH_TOKEN_SHARED_PREFS, Context.MODE_PRIVATE)
    }

    override fun saveAuthHash(hash: String) {
        sharedPrefs.edit()
                .putString(AUTH_HASH, hash)
                .apply()
    }

    override fun loadAuthHash(): String {
        if (sharedPrefs.contains(AUTH_HASH)) {
            return sharedPrefs.getString(AUTH_HASH, "")
        } else {
            throw DataDoesNotExistException()
        }
    }

    override fun saveToken(authToken: AuthToken) {
        sharedPrefs.edit()
                .putString(TOKEN, authToken.token)
                .putLong(ID, authToken.id)
                .apply()
    }

    override fun loadToken(): AuthToken {
        if (hasCredentials()) {
            val id = sharedPrefs.getLong(ID, 0L)
            val token = sharedPrefs.getString(TOKEN, "")

            return AuthToken(id, token)
        } else {
            throw DataDoesNotExistException()
        }
    }

    override fun hasCredentials(): Boolean =
            sharedPrefs.contains(TOKEN) && sharedPrefs.contains(ID) && sharedPrefs.contains(AUTH_HASH)

    override fun clearData() {
        sharedPrefs.edit()
                .remove(ID)
                .remove(TOKEN)
                .remove(AUTH_HASH)
                .apply()
    }
}