package eu.anifantakis.cmpmasterdetail.core.presentation

import androidx.navigation.NavHostController

inline fun <reified T : Any> NavHostController.popAndNavigate(popTo: T, navigate: T) {
    this.navigate(navigate) {
        popUpTo(popTo) {
            inclusive = true
            saveState = true
        }
        restoreState = true
    }
}
