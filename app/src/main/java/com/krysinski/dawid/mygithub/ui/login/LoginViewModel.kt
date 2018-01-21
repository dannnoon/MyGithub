package com.krysinski.dawid.mygithub.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.krysinski.dawid.mygithub.tool.SingleLiveEvent
import com.krysinski.dawid.mygithub.ui.login.data.LoginCommand
import com.krysinski.dawid.mygithub.ui.login.data.LoginError
import com.krysinski.dawid.mygithub.ui.login.data.LoginViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class LoginViewModel : ViewModel() {

    private val interactor = LoginInteractor()
    private val disposables = CompositeDisposable()

    private val mutableViewState: MutableLiveData<LoginViewState> = MutableLiveData()
    val viewState: LiveData<LoginViewState> = mutableViewState

    private val mutableCommand: SingleLiveEvent<LoginCommand> = SingleLiveEvent()
    val command: LiveData<LoginCommand> = mutableCommand

    init {
        mutableViewState.value = LoginViewState()
    }

    fun checkIfLoggedIn() {
        signingInViewState(true)

        if (interactor.checkIfLoggedIn()) {
            mutableCommand.value = LoginCommand.LoggedIn()
        } else {
            signingInViewState(false)
        }
    }

    fun signIn(password: String) {
        val username = viewState.value?.username

        username?.let {
            val authHash = interactor.createAuthHash(it, password)
            interactor.saveAuthHash(authHash)

            disposables += interactor.signIn()
                    .doOnSubscribe { signingInViewState(true) }
                    .doAfterTerminate { signingInViewState(false) }
                    .subscribe(
                            {
                                interactor.saveToken(it.token)
                                mutableCommand.value = LoginCommand.LoggedIn()
                            },
                            {
                                Log.e("SignIn", "Sign in error", it)
                                interactor.clearAuthData()
                                mutableCommand.value = LoginCommand.FailedLoggingIn()
                            }
                    )
        }
    }

    fun updateUsername(username: String) {
        viewState.value?.username = username
    }

    private fun signingInViewState(signingIn: Boolean) {
        mutableViewState.value = viewState.value?.copy(signingIn = signingIn)
    }

    private fun setError(error: LoginError) {
        mutableViewState.value = viewState.value?.copy(error = error, signingIn = false)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}