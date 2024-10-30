package org.track.fit.common.provider

data class GoogleAccount(
    val token: String,
    val displayName: String = "",
    val profileImageUrl: String? = null,
    val accessToken:String? = null,
)
