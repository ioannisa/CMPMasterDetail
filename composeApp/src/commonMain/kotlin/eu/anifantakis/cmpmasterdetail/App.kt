package eu.anifantakis.cmpmasterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import eu.anifantakis.cmpmasterdetail.app.NavigationRoot
import eu.anifantakis.cmpmasterdetail.app.global_state.presentation.GlobalStateContainer
import eu.anifantakis.cmpmasterdetail.app.global_state.presentation.LocalGlobalState
import eu.anifantakis.cmpmasterdetail.app.global_state.presentation.LocalGlobalStateContainer
import eu.anifantakis.cmpmasterdetail.core.presentation.MyAppTheme
import eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.components.AppLoadingIndicator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import androidx.compose.runtime.getValue

@Composable
@Preview
fun App() {

    val globalStateContainer = koinInject<GlobalStateContainer>()

    MyAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)

        ) {
            CompositionLocalProvider(
                // Provide the container itself for action dispatching
                LocalGlobalStateContainer provides globalStateContainer,

                // Provide state flow for consuming state
                LocalGlobalState provides globalStateContainer.state
            ) {
                AppContent()
            }
        }
    }
}


@Composable
fun AppContent() {
    val navController = rememberNavController()
    val globalState by LocalGlobalState.current.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                //.safeDrawingPadding()
        ) {
            NavigationRoot(navController)
        }
        AppLoadingIndicator(
            isLoading =  globalState.isLoading,
            isCritical = true
        )
    }
}