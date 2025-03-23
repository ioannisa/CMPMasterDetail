package eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.components

import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    // No back button available on this platform, so do nothing
}