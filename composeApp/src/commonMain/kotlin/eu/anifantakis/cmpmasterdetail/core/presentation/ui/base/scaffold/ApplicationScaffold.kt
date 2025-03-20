package eu.anifantakis.cmpmasterdetail.core.presentation.ui.base.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ApplicationScaffold(
    scaffoldViewModel: ScaffoldViewModel = koinViewModel(),
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val title by scaffoldViewModel.title.collectAsStateWithLifecycle()
    val onBackPress by scaffoldViewModel.onBackPress.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { if (title != null) AppTopAppBar(title, onBackPress) },
        bottomBar = { AppBottomNavBar(navController) }
    ) { paddingValues ->
        content(paddingValues)
    }
}