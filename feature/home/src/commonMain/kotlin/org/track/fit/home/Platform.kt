package org.track.fit.home

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform