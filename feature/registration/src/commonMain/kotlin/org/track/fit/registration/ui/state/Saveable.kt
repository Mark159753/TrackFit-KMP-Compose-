package org.track.fit.registration.ui.state

interface Saveable {

    val step:RegistrationStep

    fun save()
}