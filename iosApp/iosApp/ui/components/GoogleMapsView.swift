//
//  GoogleMapsView.swift
//  iosApp
//
//  Created by Mark Melnarowycz on 24.09.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import GoogleMaps
import ComposeApp

struct GoogleMapsView: UIViewRepresentable {

    @ObservedObject var state:IosLocationState = IosLocationState()
    
    private let zoom: Float = 12.0
    
    func makeUIView(context: Self.Context) -> GMSMapView {
        let camera = GMSCameraPosition.camera(
            withLatitude: state.currentLocation?.lat ?? 49.843181,
            longitude: state.currentLocation?.lng ?? 24.026281,
            zoom: zoom)
        
        
        let mapView = GMSMapView.map(withFrame: CGRect.zero, camera: camera)
        
        mapView.delegate = context.coordinator
        
        return mapView
    }
    
    func updateUIView(_ mapView: GMSMapView, context: Context) {
        if (state.trackUserPosition && state.currentLocation != nil){
            let newCameraPosition = GMSCameraPosition.camera(
                withLatitude: state.currentLocation?.lat ?? 49.843181,
                longitude: state.currentLocation?.lng ?? 24.026281,
                zoom: 16.0
            )
            mapView.animate(to: newCameraPosition)
        }
        
        state.tracks.forEach { track in
            let path = GMSMutablePath()
            
            print("TRACK -> \(track)")
            
            track.route.forEach { point in
                let coordinate = CLLocationCoordinate2D(latitude: point.lat, longitude: point.lng)
                path.add(coordinate)
            }
            
            let polyline = GMSPolyline(path: path)
            polyline.strokeColor = .red
            polyline.strokeWidth = 3.0
            polyline.map = mapView
        }
    }
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self, state: state)
    }
    
    
    class Coordinator: NSObject, GMSMapViewDelegate {
       var parent: GoogleMapsView
        var state:IosLocationState
       
       init(_ parent: GoogleMapsView, state:IosLocationState) {
           self.parent = parent
           self.state = state
       }
       
       // Observe camera move start
       func mapView(_ mapView: GMSMapView, willMove gesture: Bool) {
           if gesture {
               state.onToggleTrackUser(track: false)
           }
       }
           
   }
}
