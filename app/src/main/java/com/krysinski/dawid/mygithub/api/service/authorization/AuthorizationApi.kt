package com.krysinski.dawid.mygithub.api.service.authorization

import com.krysinski.dawid.mygithub.api.data.AuthRequestDTO
import com.krysinski.dawid.mygithub.api.data.AuthResponseDTO
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthorizationApi {

    @POST("authorizations")
    fun authorize(
            @Body authRequestDTO: AuthRequestDTO
    ): Single<AuthResponseDTO>

    @DELETE("authorizations/{id}")
    fun deauthorize(
            @Path("id") id: Long
    ): Completable
}