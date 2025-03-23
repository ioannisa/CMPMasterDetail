package eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription

/**
 * A Circular Progress Indicator that blocks background touches
 * and also blocks "back button" press while loading for critical loading operations.
 */
@Composable
fun AppLoadingIndicator(isLoading: Boolean, isCritical: Boolean) {
    if (isLoading) {
        if (isCritical) {
            // If critical, block back button
            BackHandler(enabled = true) { }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                // Go full-screen
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
                // Block unintended taps behind the full-screen Box
                .pointerInput(Unit) {
                    detectTapGestures { }
                }
                // Accessibility: convey what's happening on screen
                .semantics {
                    contentDescription = "Processing..."
                    stateDescription = "Please wait"
                }
        ) {
            // The progress indicator shown at the center of the full-screen Box
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
expect fun BackHandler(enabled: Boolean, onBack: () -> Unit)