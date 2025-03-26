package eu.anifantakis.cmpmasterdetail.core.data.battery

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import platform.UIKit.*
import kotlin.math.roundToInt
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue

actual class BatteryManager {
    actual  fun getBatteryLevel(): Int {
        UIDevice.currentDevice.batteryMonitoringEnabled = true
        val batteryLevel = UIDevice.currentDevice.batteryLevel

        return (batteryLevel * 100).roundToInt()
    }

    actual fun observeBatteryLevel(): Flow<Int> = callbackFlow {
        // Ensure monitoring is enabled (needs to be done once)
        UIDevice.currentDevice.batteryMonitoringEnabled = true

        // --- Send the initial value ---
        val initialLevel = UIDevice.currentDevice.batteryLevel
        // batteryLevel returns -1.0 if monitoring is disabled OR state is unknown
        if (initialLevel != -1.0f) {
            trySend((initialLevel * 100).roundToInt())
        }
        // --- End sending initial value ---

        // Create an observer for battery level changes
        val observer = NSNotificationCenter.defaultCenter.addObserverForName(
            name = UIDeviceBatteryLevelDidChangeNotification,
            `object` = null, // Listen for notifications from any sender
            queue = NSOperationQueue.mainQueue // Use main queue for UI updates if needed, or background
        ) { _ -> // We don't need the notification object itself here
            val currentLevel = UIDevice.currentDevice.batteryLevel
            if (currentLevel != -1.0f) {
                // Use trySend which is non-suspending
                trySend((currentLevel * 100).roundToInt()).isSuccess // Or handle failure
            }
        }

        // This block is executed when the Flow collector cancels or completes.
        awaitClose {
            // Remove the observer to prevent leaks
            NSNotificationCenter.defaultCenter.removeObserver(observer)
            // Optionally, you could disable monitoring if you are sure no other part
            // of your app needs it, but often it's fine to leave enabled.
            // UIDevice.currentDevice.batteryMonitoringEnabled = false
        }
    }
}