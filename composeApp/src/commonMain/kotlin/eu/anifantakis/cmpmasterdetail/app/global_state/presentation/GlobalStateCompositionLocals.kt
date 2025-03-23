package eu.anifantakis.cmpmasterdetail.app.global_state.presentation

import androidx.compose.runtime.compositionLocalOf
import eu.anifantakis.cmpmasterdetail.app.global_state.domain.GlobalState
import kotlinx.coroutines.flow.StateFlow

/**
 * CompositionLocal for the GlobalStateContainer itself
 * This allows dispatching actions from anywhere in the composition tree
 */
val LocalGlobalStateContainer = compositionLocalOf<GlobalStateContainer> {
    error("No GlobalStateContainer provided. Ensure it is provided at the top of your composition.")
}

/**
 * CompositionLocal for the current GlobalState
 * This provides read-only access to the current state
 */
val LocalGlobalState = compositionLocalOf<StateFlow<GlobalState>> {
    error("No GlobalState provided. Ensure it is provided at the top of your composition.")
}