package eu.anifantakis.cmpmasterdetail.app

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import eu.anifantakis.cmpmasterdetail.core.presentation.ui.base.scaffold.ApplicationScaffold
import eu.anifantakis.cmpmasterdetail.movies.moviesGraph
import kotlinx.serialization.Serializable

sealed interface NavGraph {
    @Serializable data object Movies: NavGraph
}

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    ApplicationScaffold(navController = navController) { scaffoldPadding ->
        NavHost(
            navController = navController,
            startDestination = NavGraph.Movies,
            modifier = Modifier.padding(
                paddingValues = scaffoldPadding
            )
        ) {
            moviesGraph(navController, scaffoldPadding)
        }
    }
}