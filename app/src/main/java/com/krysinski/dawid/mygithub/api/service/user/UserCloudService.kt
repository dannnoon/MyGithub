package com.krysinski.dawid.mygithub.api.service.user

import com.krysinski.dawid.mygithub.api.mapper.UserDetailsDataMapper
import com.krysinski.dawid.mygithub.domain.data.UserDetails
import com.krysinski.dawid.mygithub.domain.service.UserService
import io.reactivex.Single

class UserCloudService(private val userApi: UserApi) : UserService {

    private val userDetailsDataMapper = UserDetailsDataMapper()

    override fun userDetails(): Single<UserDetails> = userApi
            .userDetails()
            .map { userDetailsDataMapper.apply(it) }
}