package eu.anifantakis.cmpmasterdetail.core.presentation.composition_locals


import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

// State holder for bottom bar visibility
class BottomBarState {
    val isVisible = mutableStateOf(true)

    fun toggle() {
        isVisible.value = !isVisible.value
    }

    fun show() {
        isVisible.value = true
    }

    fun hide() {
        isVisible.value = false
    }
}

// Create a CompositionLocal to provide this state
val LocalBottomBarState = compositionLocalOf { BottomBarState() }