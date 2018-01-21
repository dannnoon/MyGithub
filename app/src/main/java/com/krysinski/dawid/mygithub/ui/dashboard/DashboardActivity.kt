package com.krysinski.dawid.mygithub.ui.dashboard

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.krysinski.dawid.mygithub.R
import com.krysinski.dawid.mygithub.domain.data.UserDetails
import com.krysinski.dawid.mygithub.ui.dashboard.data.DashboardCommand
import com.krysinski.dawid.mygithub.ui.dashboard.data.DashboardViewState
import com.krysinski.dawid.mygithub.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    companion object {

        fun getIntent(context: Context) = Intent(context, DashboardActivity::class.java)
    }

    private val viewModel: DashboardViewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(DashboardViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        initView()
        observeViewModel()

        viewModel.userDetails()
    }

    private fun initView() {
        dashboardRefreshLayout.setOnRefreshListener { viewModel.userDetails() }
        dashboardSignOutButton.setOnClickListener { viewModel.signOut() }
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(this, Observer {
            it?.let { handleViewState(it) }
        })

        viewModel.command.observe(this, Observer {
            it?.let { handleCommand(it) }
        })
    }

    private fun handleViewState(viewState: DashboardViewState) {
        handleLoading(viewState.loading)
        handleUserDetails(viewState.userDetails)
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            dashboardRefreshLayout.isRefreshing = true
            dashboardUserInfoContainer.visibility = View.GONE
            dashboardRepositories.isEnabled = false
            dashboardStars.isEnabled = false
            dashboardFollowers.isEnabled = false
            dashboardFollowing.isEnabled = false
        } else {
            dashboardRefreshLayout.isRefreshing = false
            dashboardUserInfoContainer.visibility = View.VISIBLE
            dashboardRepositories.isEnabled = true
            dashboardStars.isEnabled = true
            dashboardFollowers.isEnabled = true
            dashboardFollowing.isEnabled = true
        }
    }

    private fun handleUserDetails(userDetails: UserDetails?) {
        userDetails?.let {
            loadUserIcon(it)
            dashboardUserLogin.text = it.login

            dashboardRepositories.text = getString(R.string.repositories_count).format(it.repositories)
            dashboardFollowers.text = getString(R.string.followers_count).format(it.followers)
            dashboardFollowing.text = getString(R.string.following_count).format(it.following)
        }
    }

    private fun loadUserIcon(it: UserDetails) {
        Glide
                .with(this)
                .load(Uri.parse(it.iconUrl))
                .into(dashboardUserIcon)
    }

    private fun handleCommand(it: DashboardCommand) {
        when (it) {
            is DashboardCommand.SignOutCommand -> handleSignOutCommand()
        }
    }

    private fun handleSignOutCommand() {
        startActivity(LoginActivity.getIntent(this))
    }
}