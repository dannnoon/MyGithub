package com.krysinski.dawid.mygithub.api.mapper

import com.krysinski.dawid.mygithub.api.data.AuthResponseDTO
import com.krysinski.dawid.mygithub.domain.data.AuthResponse
import com.krysinski.dawid.mygithub.domain.data.AuthToken

class AuthResponseDataMapper : DataMapper<AuthResponseDTO, AuthResponse> {

    override fun apply(data: AuthResponseDTO): AuthResponse = AuthResponse(
            AuthToken(data.id, data.token)
    )
}