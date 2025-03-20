package eu.anifantakis.cmpmasterdetail.setup.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import eu.anifantakis.cmpmasterdetail.core.presentation.MyAppTheme

@Composable
fun SetupScreen() {

    LaunchedEffect(Unit) {
        println("SetupScreen launched")
    }

    MyAppTheme {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Setup Screen",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 48.sp
            )
        }
    }
}