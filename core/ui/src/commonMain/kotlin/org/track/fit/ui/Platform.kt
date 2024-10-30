package org.track.fit.ui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform