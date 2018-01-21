package com.krysinski.dawid.mygithub.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.textChanges
import com.krysinski.dawid.mygithub.R
import com.krysinski.dawid.mygithub.tool.transformTo
import com.krysinski.dawid.mygithub.ui.dashboard.DashboardActivity
import com.krysinski.dawid.mygithub.ui.login.data.LoginCommand
import com.krysinski.dawid.mygithub.ui.login.data.LoginError
import com.krysinski.dawid.mygithub.ui.login.data.LoginViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {

        private const val BUTTON_ANIMATION_DURATION = 100L

        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    private val disposables = CompositeDisposable()

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initObservers()
        initSignInButton()
        observeViewModel()

        viewModel.checkIfLoggedIn()
    }

    private fun initObservers() {
        disposables += loginUsername
                .textChanges()
                .subscribe { viewModel.updateUsername(it.toString()) }
    }

    private fun initSignInButton() {
        loginSignInButton.setOnClickListener {
            it.transformTo(
                    loginProgress,
                    { viewModel.signIn(getPassword()) },
                    BUTTON_ANIMATION_DURATION
            )
        }
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(this, Observer {
            it?.let { handleViewState(it) }
        })

        viewModel.command.observe(this, Observer {
            it?.let { handleCommand(it) }
        })
    }

    private fun handleViewState(viewState: LoginViewState) {
        handleSigningIn(viewState.signingIn)
        handleError(viewState.error)
        handleUsername(viewState.username)
    }

    private fun handleCommand(command: LoginCommand) {
        when (command) {
            is LoginCommand.LoggedIn -> handleLoginSucceedCommand()
            is LoginCommand.FailedLoggingIn -> handleLoginInFailedCommand()
        }
    }

    private fun handleSigningIn(signingIn: Boolean) {
        if (signingIn) {
            loginUsername.isEnabled = false
            loginPassword.isEnabled = false
            loginSignInButton.visibility = View.GONE
            loginProgress.visibility = View.VISIBLE
        } else {
            loginUsername.isEnabled = true
            loginPassword.isEnabled = true
            loginSignInButton.visibility = View.VISIBLE
            loginProgress.visibility = View.GONE
        }
    }

    private fun handleError(error: LoginError?) {
        //TODO
    }

    private fun handleUsername(username: String) {
        loginUsername.setText(username, TextView.BufferType.NORMAL)
    }

    private fun handleLoginSucceedCommand() {
        startActivity(DashboardActivity.getIntent(this))
        finish()
    }

    private fun handleLoginInFailedCommand() {
        //TODO
    }

    private fun getPassword() = loginPassword.text.toString()

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}
