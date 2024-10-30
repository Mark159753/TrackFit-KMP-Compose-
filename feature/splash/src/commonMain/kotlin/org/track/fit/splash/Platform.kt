package org.track.fit.splash

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform