package org.track.fit.local

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform