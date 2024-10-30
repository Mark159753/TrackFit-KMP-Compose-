package org.track.fit.track

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform