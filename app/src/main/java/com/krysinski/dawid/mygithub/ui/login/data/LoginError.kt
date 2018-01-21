package com.krysinski.dawid.mygithub.ui.login.data

sealed class LoginError {

    class UsernameValidationError : LoginError()

    class PasswordValidationError : LoginError()

    class CredentialsError : LoginError()
}