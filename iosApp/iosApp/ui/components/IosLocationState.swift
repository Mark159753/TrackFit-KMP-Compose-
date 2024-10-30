//
//  IosLocationState.swift
//  iosApp
//
//  Created by Mark Melnarowycz on 17.10.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import ComposeApp

class IosLocationState:ObservableObject {
    
    private let state = TrackDiHelper().provideLocationState()
    private  var wathers:[Closeable] = []
    
    @Published
    var currentLocation:Location? = nil

    @Published
    var isMyLocationEnabled:Bool = false

    @Published
    var tracks:[RouteModel] = []

    @Published
    var trackUserPosition:Bool = true
    
    init(){
        observeCurrentLocation()
        observeIsMyLocationEnabled()
        observeTracks()
        observeTrackUserPosition()
    }
    
    func onToggleTrackUser(track:Bool){
        state.onToggleTrackUser(track: track)
    }
    
    
    private func observeCurrentLocation(){
        let watcher = CFlowKt.wrap(state.currentLocation).watch(block: { location in
            self.currentLocation = location as? Location
        })
        wathers.append(watcher)
    }
    
    private func observeIsMyLocationEnabled(){
        let watcher = CFlowKt.wrap(state.isMyLocationEnabled).watch(block: { enabled in
            self.isMyLocationEnabled = enabled as! Bool
        })
        wathers.append(watcher)
    }
    
    private func observeTracks(){
        let watcher = CFlowKt.wrap(state.tracks).watch(block: { t in
            self.tracks = t as! [RouteModel]
        })
        wathers.append(watcher)
    }
    
    private func observeTrackUserPosition(){
        let watcher = CFlowKt.wrap(state.trackUserPosition).watch(block: { t in
            self.trackUserPosition = t as! Bool
        })
        wathers.append(watcher)
    }
    
    deinit{
        wathers.forEach{ closeble in closeble.close() }
    }
    
    
}
