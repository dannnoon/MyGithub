package com.krysinski.dawid.mygithub.api.data

import com.squareup.moshi.Json

data class AuthResponseDTO(
        @Json(name = "id") val id: Long,
        @Json(name = "url") val url: String,
        @Json(name = "app") val app: AuthAppDTO,
        @Json(name = "token") val token: String,
        @Json(name = "hashed_token") val hashedToken: String,
        @Json(name = "token_last_eight") val tokenLastEight: String,
        @Json(name = "note") val note: String,
        @Json(name = "note_url") val noteUrl: String?,
        @Json(name = "created_at") val createdAt: String,
        @Json(name = "updated_at") val updatedAt: String,
        @Json(name = "scopes") val scopes: List<String>,
        @Json(name = "fingerprint") val fingerprint: String?
)