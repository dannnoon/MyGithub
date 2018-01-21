package com.krysinski.dawid.mygithub.domain.service

import com.krysinski.dawid.mygithub.domain.data.AuthResponse
import io.reactivex.Completable
import io.reactivex.Single

interface AuthorizationService {

    fun authorize(): Single<AuthResponse>

    fun deauthorize(id: Long): Completable
}