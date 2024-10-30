//
//  GoogleAuthProviderImpl.swift
//  iosApp
//
//  Created by Mark Melnarowycz on 19.09.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import ComposeApp
import Combine
import UIKit
import GoogleSignIn
import FirebaseCore

class GoogleAuthProviderImpl: GoogleAuthProvider {
    
    func signIn() async throws -> GoogleAccount? {
        guard let rootViewController = UIApplication.shared.keyWindow?.rootViewController else {
            return nil
        }

        return try await withCheckedThrowingContinuation { continuation in
            guard let clientID = FirebaseApp.app()?.options.clientID else { return }

            // Create Google Sign In configuration object.
            let config = GIDConfiguration(clientID: clientID)
            GIDSignIn.sharedInstance.configuration = config
            GIDSignIn.sharedInstance.signIn(withPresenting: rootViewController) { gidSignInResult, error in
                if let error = error {
                    print("Error: \(error.localizedDescription)")
                    continuation.resume(returning: nil)
                    return
                }
                
                guard let idToken = gidSignInResult?.user.idToken?.tokenString,
                      let profile = gidSignInResult?.user.profile else {
                    continuation.resume(returning: nil)
                    return
                }
                
                guard let accessToken = gidSignInResult?.user.accessToken.tokenString,
                      let profile = gidSignInResult?.user.profile else {
                    continuation.resume(returning: nil)
                    return
                }

                let googleUser = GoogleAccount(
                    token: idToken,
                    displayName: profile.name ?? "",
                    profileImageUrl: profile.imageURL(withDimension: 320)?.absoluteString,
                    accessToken: accessToken
                )
                
                continuation.resume(returning: googleUser)
            }
        }
    }
    
    func signIn(completionHandler: @escaping (GoogleAccount?, (any Error)?) -> Void) {
        guard let rootViewController = UIApplication.shared.keyWindow?.rootViewController else {
            completionHandler(nil, nil)
            return
        }
        
        GIDSignIn.sharedInstance.signIn(withPresenting: rootViewController) { gidSignInResult, error in
            if let error = error {
                print("Error: \(error.localizedDescription)")
                completionHandler(nil, nil)
                return
            }
            
            guard let idToken = gidSignInResult?.user.idToken?.tokenString,
                  let profile = gidSignInResult?.user.profile else {
                completionHandler(nil, nil)
                return
            }
            
            guard let accessToken = gidSignInResult?.user.accessToken.tokenString,
                  let profile = gidSignInResult?.user.profile else {
                completionHandler(nil , nil)
                return
            }
            
            let googleUser = GoogleAccount(
                token: idToken,
                displayName: profile.name ?? "",
                profileImageUrl: profile.imageURL(withDimension: 320)?.absoluteString,
                accessToken: accessToken
            )
            
            completionHandler(googleUser, nil)
        }
    }
    

}
