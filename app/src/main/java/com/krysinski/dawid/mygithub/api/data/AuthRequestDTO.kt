package com.krysinski.dawid.mygithub.api.data

import com.squareup.moshi.Json

data class AuthRequestDTO(
        @Json(name = "scopes") val scopes: List<String>,
        @Json(name = "note") val note: String,
        @Json(name = "note_url") val noteUrl: String?,
        @Json(name = "client_id") val clientId: String?,
        @Json(name = "client_secret") val clientSecret: String?,
        @Json(name = "fingerprint") val fingerprint: String?
) {
    companion object {

        fun standardAuth(): AuthRequestDTO = AuthRequestDTO(
                listOf("public_repo"),
                "MyGithub app authorization",
                null,
                null,
                null,
                null
        )
    }
}