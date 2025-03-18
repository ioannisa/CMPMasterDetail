import SwiftUI

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
                //.statusBar(hidden: true)
                .edgesIgnoringSafeArea(.all)
                .ignoresSafeArea(.keyboard)
        }
    }
}
