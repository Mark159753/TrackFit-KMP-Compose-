//
//  GoogleMap.swift
//  iosApp
//
//  Created by Mark Melnarowycz on 24.09.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct GoogleMap:View {
    
    var body: some View {
       ZStack {
           GoogleMapsView()
       }
       .ignoresSafeArea(.all)
   }
}
