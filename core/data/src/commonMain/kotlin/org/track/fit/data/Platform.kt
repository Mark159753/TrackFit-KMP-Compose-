package org.track.fit.data

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform