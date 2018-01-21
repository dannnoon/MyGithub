package com.krysinski.dawid.mygithub.api.service.user

import com.krysinski.dawid.mygithub.api.data.UserDetailsDTO
import com.krysinski.dawid.mygithub.api.data.UserRepositoryDTO
import io.reactivex.Single
import retrofit2.http.GET

interface UserApi {

    @GET("user")
    fun userDetails(): Single<UserDetailsDTO>

    @GET("user/repos")
    fun userRepositories(): Single<List<UserRepositoryDTO>>
}