package eu.anifantakis.cmpmasterdetail.app.global_state.presentation

import eu.anifantakis.cmpmasterdetail.app.global_state.domain.GlobalAction
import eu.anifantakis.cmpmasterdetail.app.global_state.domain.GlobalState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Global state container that manages app-wide state using MVI patterns
 */
class GlobalStateContainer(initialState: GlobalState = GlobalState()) {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<GlobalState> = _state.asStateFlow()

    /**
     * Process a global action to update state
     */
    fun dispatch(action: GlobalAction) {
        val oldState = _state.value
        val newState = reduce(oldState, action)

        // Only update if state actually changed
        if (oldState != newState) {
            _state.value = newState
        }
    }

    /**
     * Pure reducer function that creates a new state based on the current state and an action
     */
    private fun reduce(currentState: GlobalState, action: GlobalAction): GlobalState {
        return when (action) {
            is GlobalAction.ShowLoading -> currentState.copy(
                isLoading = true
            )

            is GlobalAction.HideLoading -> currentState.copy(
                isLoading = false
            )

            is GlobalAction.UpdateBatteryLevel -> currentState.copy(
                batteryLevel = action.batteryLevel
            )
        }
    }

    /**
     * Restore state (typically after process death)
     */
    fun restoreState(newState: GlobalState) {
        _state.value = newState
    }
}