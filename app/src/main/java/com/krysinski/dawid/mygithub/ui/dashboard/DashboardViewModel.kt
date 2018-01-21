package com.krysinski.dawid.mygithub.ui.dashboard

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.krysinski.dawid.mygithub.domain.data.UserDetails
import com.krysinski.dawid.mygithub.tool.SingleLiveEvent
import com.krysinski.dawid.mygithub.ui.dashboard.data.DashboardCommand
import com.krysinski.dawid.mygithub.ui.dashboard.data.DashboardViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class DashboardViewModel : ViewModel() {

    private val interactor = DashboardInteractor()
    private val disposables = CompositeDisposable()

    private val mutableViewState: MutableLiveData<DashboardViewState> = MutableLiveData()
    val viewState: LiveData<DashboardViewState> = mutableViewState

    private val mutableCommand: SingleLiveEvent<DashboardCommand> = SingleLiveEvent()
    val command: LiveData<DashboardCommand> = mutableCommand

    init {
        mutableViewState.value = DashboardViewState()
    }

    fun signOut() {
        disposables += interactor.signOut()
                .subscribe(
                        {
                            interactor.clearAuthData()
                            mutableCommand.value = DashboardCommand.SignOutCommand()
                        },
                        { Log.e("SignOut", "Error during sign out.", it) }
                )
    }

    fun userDetails() {
        disposables += interactor.userDetails()
                .doOnSubscribe { loading(true) }
                .doAfterTerminate { loading(false) }
                .subscribe(
                        { userDetails(it) },
                        {}
                )
    }

    private fun loading(isLoading: Boolean) {
        mutableViewState.value = viewState.value?.copy(loading = isLoading)
    }

    private fun userDetails(userDetails: UserDetails?) {
        mutableViewState.value = viewState.value?.copy(userDetails = userDetails)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}