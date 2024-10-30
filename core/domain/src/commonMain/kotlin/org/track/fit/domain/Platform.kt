package org.track.fit.domain

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform