package eu.anifantakis.cmpmasterdetail.app.global_state.presentation

import androidx.lifecycle.ViewModel
import eu.anifantakis.cmpmasterdetail.app.global_state.domain.GlobalAction
import eu.anifantakis.cmpmasterdetail.app.global_state.domain.GlobalState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import org.koin.mp.KoinPlatform.getKoin

/**
 * BaseViewModel that provides global state management capabilities to all feature ViewModels
 */
abstract class BaseGlobalViewModel(
    protected val globalStateContainer: GlobalStateContainer = getKoin().get<GlobalStateContainer>()
) : ViewModel() {

    // Direct access to global state
    protected val globalState: StateFlow<GlobalState> = globalStateContainer.state

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
}