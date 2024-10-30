import SwiftUI
import Firebase
import ComposeApp
import FirebaseAuth
import GoogleMaps

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    init(){
        DiHelperKt.doInitKoin(iosDependenciesProvider: IosDependenciesProviderImpl())
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
  func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
      FirebaseApp.configure()
      GMSServices.provideAPIKey("YOUR_MAP_API_KEY")
      return true
  }
}
