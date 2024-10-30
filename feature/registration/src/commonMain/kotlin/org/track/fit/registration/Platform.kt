package org.track.fit.registration

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform