package eu.anifantakis.cmpmasterdetail

import androidx.compose.ui.window.ComposeUIViewController
import eu.anifantakis.cmpmasterdetail.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}