package com.krysinski.dawid.mygithub.ui.login.data

data class LoginViewState(
        val signingIn: Boolean = false,
        val error: LoginError? = null,
        var username: String = ""
)