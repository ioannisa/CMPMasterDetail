package eu.anifantakis.cmpmasterdetail.setup.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import eu.anifantakis.cmpmasterdetail.core.presentation.MyAppTheme
import eu.anifantakis.cmpmasterdetail.core.presentation.composition_locals.LocalBottomBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SetupScreen() {

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        println("SetupScreen launched")
    }

    MyAppTheme {
        val bottomBarState = LocalBottomBarState.current

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = "Setup Screen",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 48.sp
                )

                Button(
                    onClick = {
                        scope.launch {
                            bottomBarState.selectTab(0)
                            // Give it a tiny delay to ensure navigation is processed
                            delay(50)
                            bottomBarState.hide()
                        }
                    }
                ) {
                    Text("APPLY")
                }
            }
        }
    }
}