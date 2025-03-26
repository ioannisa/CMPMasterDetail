package eu.anifantakis.cmpmasterdetail.core.data.battery

import kotlinx.coroutines.flow.Flow

expect class BatteryManager {
   fun getBatteryLevel(): Int
   fun observeBatteryLevel(): Flow<Int>
}