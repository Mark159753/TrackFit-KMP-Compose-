package org.track.fit.services

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform