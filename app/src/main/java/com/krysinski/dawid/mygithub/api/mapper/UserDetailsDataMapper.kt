package com.krysinski.dawid.mygithub.api.mapper

import com.krysinski.dawid.mygithub.api.data.UserDetailsDTO
import com.krysinski.dawid.mygithub.domain.data.UserDetails

class UserDetailsDataMapper : DataMapper<UserDetailsDTO, UserDetails> {

    override fun apply(data: UserDetailsDTO): UserDetails = UserDetails(
            data.login,
            data.name,
            data.avatarUrl,
            data.publicRepos + data.totalPrivateRepos,
            data.followers,
            data.following
    )
}