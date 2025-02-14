package com.project.reband.data.etc

data class OAuthData(
    val code: String,
    val data: OAuth,
    val message: String
) {
    data class OAuth(
        val jwtToken: String
    )
}
