package org.track.fit.common

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform