package org.track.fit.auth

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform