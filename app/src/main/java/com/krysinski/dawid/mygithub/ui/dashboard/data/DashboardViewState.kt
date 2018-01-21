package com.krysinski.dawid.mygithub.ui.dashboard.data

import com.krysinski.dawid.mygithub.domain.data.UserDetails

data class DashboardViewState(
        val loading: Boolean = false,
        val userDetails: UserDetails? = null
)