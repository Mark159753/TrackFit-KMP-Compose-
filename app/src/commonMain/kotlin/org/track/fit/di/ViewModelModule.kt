package org.track.fit.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.track.fit.account.ui.AccountViewModel
import org.track.fit.auth.auth.AuthViewModel
import org.track.fit.auth.signIn.SignInViewModel
import org.track.fit.auth.signUp.SignUpViewModel
import org.track.fit.home.ui.HomeViewModel
import org.track.fit.registration.ui.RegistrationViewModel
import org.track.fit.report.ui.ReportViewModel
import org.track.fit.splash.ui.SplashViewModel
import org.track.fit.track.ui.TrackViewModel
import org.track.fit.ui.MainViewModel

val viewmodelModule = module {
    viewModelOf(::SignUpViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::AccountViewModel)
    viewModelOf(::TrackViewModel)
    viewModelOf(::ReportViewModel)
    viewModelOf(::MainViewModel)
}