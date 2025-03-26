package eu.anifantakis.cmpmasterdetail.app.global_state.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.anifantakis.cmpmasterdetail.app.global_state.domain.GlobalAction
import eu.anifantakis.cmpmasterdetail.app.global_state.domain.GlobalState
import eu.anifantakis.cmpmasterdetail.core.data.battery.BatteryManager
import eu.anifantakis.cmpmasterdetail.core.presentation.toComposeState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import org.koin.mp.KoinPlatform.getKoin

/**
 * BaseViewModel that provides global state management capabilities to all feature ViewModels
 */
abstract class BaseGlobalViewModel(
    private val batteryManager: BatteryManager = getKoin().get<BatteryManager>(),
    protected val globalStateContainer: GlobalStateContainer = getKoin().get<GlobalStateContainer>()
) : ViewModel() {

    // Direct access to global state
    protected val globalState: StateFlow<GlobalState> = globalStateContainer.state
    val globalUiState by globalState.toComposeState(viewModelScope)

    /**
     * Show global loading indicator
     */
    protected fun showLoading() {
        globalStateContainer.dispatch(GlobalAction.ShowLoading)
    }

    /**
     * Hide global loading indicator
     */
    protected fun hideLoading() {
        globalStateContainer.dispatch(GlobalAction.HideLoading)
    }

    /**
     * Execute a task with automatic loading indicator
     * Returns a Result with either success or failure
     */
    protected suspend fun <T> withLoading(block: suspend () -> T): Result<T> {
        showLoading()

        return try {
            val result = block()
            Result.success(result)

        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            delay(500L)
            hideLoading()
        }
    }

    init {
        // Start observing battery level as soon as any BaseGlobalViewModel instance is created
        observeBatteryUpdates()
    }

    private fun observeBatteryUpdates() {
        viewModelScope.launch {
            batteryManager.observeBatteryLevel()
                .collect { newLevel -> // Collect emissions from the battery flow
                    // Dispatch an action to update the battery level in the global state
                    globalStateContainer.dispatch(GlobalAction.UpdateBatteryLevel(newLevel))
                }
        }
    }

    /**
     * Gets the most recently observed battery level directly from the global state.
     * This is suitable for on-demand checks.
     */
    protected fun getCurrentBatteryLevel(): Int {
        return globalState.value.batteryLevel
    }
}