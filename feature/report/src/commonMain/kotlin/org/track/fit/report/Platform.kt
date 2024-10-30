package org.track.fit.report

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform