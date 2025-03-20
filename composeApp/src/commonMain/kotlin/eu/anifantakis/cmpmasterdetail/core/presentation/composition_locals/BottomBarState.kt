package eu.anifantakis.cmpmasterdetail.core.presentation.composition_locals


import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

// State holder for bottom bar visibility
class BottomBarState {
    val isVisible = mutableStateOf(true)
    val selectedIndex = mutableIntStateOf(0)

    fun toggle() {
        isVisible.value = !isVisible.value
    }

    fun show() {
        isVisible.value = true
    }

    fun hide() {
        isVisible.value = false
    }

    fun selectTab(index: Int) {
        selectedIndex.value = index
    }
}

// Create a CompositionLocal to provide this state
val LocalBottomBarState = compositionLocalOf { BottomBarState() }