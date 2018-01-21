package com.krysinski.dawid.mygithub.ui.login.data

sealed class LoginCommand {

    class LoggedIn : LoginCommand()

    class FailedLoggingIn : LoginCommand()
}