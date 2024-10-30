package org.track.fit.common.di

import org.track.fit.common.provider.GoogleAuthProvider
import platform.UIKit.UIViewController

interface IosDependenciesProvider {

    fun getGoogleAuthProvider(): GoogleAuthProvider

    fun getGoogleMapView():UIViewController
}