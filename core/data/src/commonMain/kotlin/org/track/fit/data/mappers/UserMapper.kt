package org.track.fit.data.mappers

import dev.gitlive.firebase.auth.FirebaseUser
import org.track.fit.data.models.UserModel

fun FirebaseUser.toModel() = UserModel(
    displayName = displayName,
    email = email ?: ""
)