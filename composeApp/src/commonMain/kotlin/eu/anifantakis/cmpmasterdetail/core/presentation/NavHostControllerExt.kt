package eu.anifantakis.cmpmasterdetail.core.presentation

import androidx.navigation.NavHostController

inline fun <reified T : Any> NavHostController.popAndNavigate(popTo: String, inclusive: Boolean = true, navigate: T) {
    this.navigate(navigate) {
        popUpTo(popTo) {
            this.inclusive = inclusive
            saveState = true
        }
        restoreState = true
    }
}
