package com.krysinski.dawid.mygithub.api.service.authorization

import com.krysinski.dawid.mygithub.api.data.AuthRequestDTO
import com.krysinski.dawid.mygithub.api.mapper.AuthResponseDataMapper
import com.krysinski.dawid.mygithub.domain.data.AuthResponse
import com.krysinski.dawid.mygithub.domain.service.AuthorizationService
import io.reactivex.Completable
import io.reactivex.Single

class AuthorizationCloudService(
        private val authorizationApi: AuthorizationApi
) : AuthorizationService {

    private val authResponseDataMapper = AuthResponseDataMapper()

    override fun authorize(): Single<AuthResponse> {
        return authorizationApi
                .authorize(AuthRequestDTO.standardAuth())
                .map { authResponseDataMapper.apply(it) }
    }

    override fun deauthorize(id: Long): Completable = authorizationApi.deauthorize(id)
}