package com.krysinski.dawid.mygithub.domain.service

import com.krysinski.dawid.mygithub.domain.data.UserDetails
import io.reactivex.Single

interface UserService {

    fun userDetails(): Single<UserDetails>
}