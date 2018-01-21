package com.krysinski.dawid.mygithub.api.data

import com.squareup.moshi.Json

data class AuthAppDTO(
        @Json(name = "name") val name: String,
        @Json(name = "url") val url: String,
        @Json(name = "client_id") val clientId: String
)