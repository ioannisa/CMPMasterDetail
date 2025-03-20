package eu.anifantakis.cmpmasterdetail.setup

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import eu.anifantakis.cmpmasterdetail.app.NavGraph
import eu.anifantakis.cmpmasterdetail.setup.presentation.SetupScreen
import kotlinx.serialization.Serializable

@Serializable
sealed interface SetupNavType {
    @Serializable data object Setup: SetupNavType
}

fun NavGraphBuilder.setupGraph(navController: NavHostController, paddingValues: PaddingValues) {
    navigation<NavGraph.Setup>(
        startDestination = SetupNavType.Setup,
    ) {
        composable<SetupNavType.Setup> {
            SetupScreen()
        }
    }
}