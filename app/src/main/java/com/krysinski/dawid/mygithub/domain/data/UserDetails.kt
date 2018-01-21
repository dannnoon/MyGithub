package com.krysinski.dawid.mygithub.domain.data

data class UserDetails(
        val login: String,
        val name: String?,
        val iconUrl: String,
        val repositories: Int,
        val followers: Int,
        val following: Int
)