package org.track.fit.account

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform