package eu.anifantakis.cmpmasterdetail.core.data.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.channels.awaitClose
import android.os.BatteryManager as AndroidBatteryManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.math.roundToInt

actual class BatteryManager(private val context: Context) {

    actual  fun getBatteryLevel(): Int {

        val intentFilter  = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null, intentFilter)

        val level = batteryStatus?.getIntExtra(AndroidBatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryStatus?.getIntExtra(AndroidBatteryManager.EXTRA_SCALE, -1) ?: -1

        return (level / scale.toFloat() * 100).roundToInt()
    }


    actual fun observeBatteryLevel(): Flow<Int> = callbackFlow {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_BATTERY_CHANGED) {
                    val level = intent.getIntExtra(AndroidBatteryManager.EXTRA_LEVEL, -1)
                    val scale = intent.getIntExtra(AndroidBatteryManager.EXTRA_SCALE, -1)
                    // Ensure valid values before calculating and sending
                    if (level != -1 && scale != -1 && scale != 0) {
                        val percentage = (level / scale.toFloat() * 100).roundToInt()
                        // Use trySend which is non-suspending and safe within callbacks
                        trySend(percentage).isSuccess // Or handle failure if needed
                    }
                }
            }
        }

        // Create the filter for battery change events
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        // Register the receiver
        context.registerReceiver(receiver, intentFilter)

        // --- Send the initial value right after registration ---
        val initialStatus = context.registerReceiver(null, intentFilter) // Get sticky intent
        if (initialStatus != null) {
            val initialLevel = initialStatus.getIntExtra(AndroidBatteryManager.EXTRA_LEVEL, -1)
            val initialScale = initialStatus.getIntExtra(AndroidBatteryManager.EXTRA_SCALE, -1)
            if (initialLevel != -1 && initialScale != -1 && initialScale != 0) {
                val initialPercentage = (initialLevel / initialScale.toFloat() * 100).roundToInt()
                trySend(initialPercentage) // Send initial value
            }
        }
        // --- End sending initial value ---

        // This block is executed when the Flow collector cancels or completes.
        awaitClose {
            // Unregister the receiver to avoid memory leaks
            context.unregisterReceiver(receiver)
        }
    }
}