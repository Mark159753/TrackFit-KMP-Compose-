//
//  IosDependenciesProviderImpl.swift
//  iosApp
//
//  Created by Mark Melnarowycz on 19.09.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import ComposeApp
import SwiftUI
import ComposeApp

class IosDependenciesProviderImpl: IosDependenciesProvider {
    
    func getGoogleAuthProvider() -> GoogleAuthProvider {
        return GoogleAuthProviderImpl()
    }

    func getGoogleMapView() -> UIViewController {
        return UIHostingController(rootView: GoogleMap())
    }
    
}
