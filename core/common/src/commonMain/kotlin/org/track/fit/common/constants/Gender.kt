package org.track.fit.common.constants

enum class Gender {
    Male, Female
}

fun String.toGender() = try {
    Gender.valueOf(this)
}catch (e:Exception){
    null
}