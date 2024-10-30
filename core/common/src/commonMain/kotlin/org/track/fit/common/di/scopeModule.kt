package org.track.fit.common.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val scopeModule = module {
    factory<CoroutineScope> { CoroutineScope(Dispatchers.Main + SupervisorJob()) }
}