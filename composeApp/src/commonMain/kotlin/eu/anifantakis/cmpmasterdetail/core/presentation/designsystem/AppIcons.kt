package eu.anifantakis.cmpmasterdetail.core.presentation.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BatteryStd
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import cmpmasterdetail.composeapp.generated.resources.Res
import cmpmasterdetail.composeapp.generated.resources.car
import cmpmasterdetail.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource


object AppIcons {
    // Painter using Image Resource
    val appLoco: Painter
        @Composable
        get() = painterResource(Res.drawable.compose_multiplatform)

    // ImageVector using Image Resource
    val car: ImageVector
        @Composable
        get() = vectorResource(Res.drawable.car)

    // ImageVector using Icons Library
    val backArrow: ImageVector
        @Composable
        get() = Icons.AutoMirrored.Filled.ArrowBack

    val battery: ImageVector
        @Composable
        get() = Icons.Default.BatteryStd

    val settings: ImageVector
        @Composable
        get() = Icons.Default.Settings

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

    val visibilityOn: ImageVector
        @Composable
        get() = Icons.Outlined.Visibility

    val visibilityOff: ImageVector
        @Composable
        get() = Icons.Outlined.VisibilityOff

    val restaurant: ImageVector
        @Composable
        get() = Icons.Outlined.Restaurant

    val fastFood: ImageVector
        @Composable
        get() = Icons.Outlined.Fastfood

    val person: ImageVector
        @Composable
        get() = Icons.Outlined.Person
}