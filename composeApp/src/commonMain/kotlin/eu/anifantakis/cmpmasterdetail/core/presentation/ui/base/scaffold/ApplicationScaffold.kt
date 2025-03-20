package eu.anifantakis.cmpmasterdetail.core.presentation.ui.base.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import eu.anifantakis.cmpmasterdetail.core.presentation.composition_locals.LocalBottomBarState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ApplicationScaffold(
    scaffoldViewModel: ScaffoldViewModel = koinViewModel(),
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val title by scaffoldViewModel.title.collectAsStateWithLifecycle()
    val onBackPress by scaffoldViewModel.onBackPress.collectAsStateWithLifecycle()

    val bottomBarState = LocalBottomBarState.current
    val isBottomBarVisible by bottomBarState.isVisible

    Scaffold(
        topBar = { if (title != null) AppTopAppBar(title, onBackPress) },
        bottomBar = {
            if (isBottomBarVisible) {
                AppBottomNavBar(navController)
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}