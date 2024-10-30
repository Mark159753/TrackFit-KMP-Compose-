package org.track.fit.data.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import org.koin.dsl.module

val firebaseModule = module {
    single<FirebaseDatabase> {
        val database = Firebase.database
        database.setPersistenceEnabled(true)
        database
    }
    single<DatabaseReference> {
        val database:FirebaseDatabase = get()
        database.reference()
    }
}