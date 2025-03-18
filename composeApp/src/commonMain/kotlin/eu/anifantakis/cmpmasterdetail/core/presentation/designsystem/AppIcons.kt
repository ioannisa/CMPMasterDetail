package eu.anifantakis.cmpmasterdetail.core.presentation.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import cmpmasterdetail.composeapp.generated.resources.Res
import cmpmasterdetail.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource


object AppIcons {
    val appLoco: Painter
        @Composable
        get() = painterResource(Res.drawable.compose_multiplatform)

    val check: ImageVector
        @Composable
        get() = Icons.Default.Check

    val close: ImageVector
        @Composable
        get() = Icons.Default.Close

    val email: ImageVector
        @Composable
        get() = Icons.Outlined.Email

    val padlock: ImageVector
        @Composable
        get() = Icons.Outlined.Lock

//    val visibilityOn: ImageVector
//        @Composable
//        get() = androidx.compose.material.icons.Icons.Outlined.Visibility
//
//    val visibilityOff: ImageVector
//        @Composable
//        get() = androidx.compose.material.icons.Icons.Outlined.VisibilityOff
//
//    val restaurant: ImageVector
//        @Composable
//        get() = androidx.compose.material.icons.Icons.Outlined.Restaurant
//
//    val fastFood: ImageVector
//        @Composable
//        get() = androidx.compose.material.icons.Icons.Outlined.Fastfood

    val person: ImageVector
        @Composable
        get() = Icons.Outlined.Person
}