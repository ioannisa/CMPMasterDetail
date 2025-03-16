package eu.anifantakis.cmpmasterdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import eu.anifantakis.cmpmasterdetail.app.NavigationRoot
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        Box(
            modifier = Modifier.safeDrawingPadding()
        ) {
            NavigationRoot(navController)
        }
    }
}