package eu.anifantakis.cmpmasterdetail.core.presentation.ui.base.scaffold

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import eu.anifantakis.cmpmasterdetail.app.NavGraph
import eu.anifantakis.cmpmasterdetail.core.presentation.AppColor
import eu.anifantakis.cmpmasterdetail.core.presentation.composition_locals.LocalBottomBarState
import eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.AppIcons
import eu.anifantakis.cmpmasterdetail.core.presentation.popAndNavigate

data class BottomNavigationItem(
    val label: String = "",
    val icon: Any? = null,
    val destination: NavGraph = NavGraph.Setup
) {
    @Composable
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Totem",
                icon = AppIcons.car,
                destination = NavGraph.Movies,

            ),
            BottomNavigationItem(
                label = "Settings",
                icon = AppIcons.settings,
                destination = NavGraph.Setup
            ),
        )
    }
}

@Composable
fun AppBottomNavBar(navController: NavHostController) {
    val bottomBarState = LocalBottomBarState.current
    val navigationSelectedItem by bottomBarState.selectedIndex

    // Get the navigation items outside the NavigationBar for use in the effect
    val navigationItems = BottomNavigationItem().bottomNavigationItems()

    // Track whether navigation is in progress to prevent loops
    val isNavigating = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(navigationSelectedItem) {
        // Only proceed if not already navigating
        if (!isNavigating.value && navigationSelectedItem >= 0 && navigationSelectedItem < navigationItems.size) {
            isNavigating.value = true

            val destination = navigationItems[navigationSelectedItem].destination
            navController.graph.findStartDestination().route?.let { startRoute ->
                navController.popAndNavigate(
                    popTo = startRoute,
                    inclusive = false,
                    navigate = destination
                )
            }

            // Reset navigation flag
            isNavigating.value = false
        }
    }

    NavigationBar(
        containerColor = AppColor.DarkGrey,
        contentColor = AppColor.BrightGrey,
        tonalElevation = 4.dp
    ) {
        navigationItems.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = index == navigationSelectedItem,
                label = { Text(navigationItem.label) },
                icon = {
                    navigationItem.icon?.let { icon ->
                        when (icon) {
                            is ImageVector -> Icon(
                                imageVector = icon,
                                contentDescription = navigationItem.label
                            )

                            is Painter -> Icon(
                                painter = icon,
                                contentDescription = navigationItem.label
                            )
                        }
                    }
                },
                onClick = {
                    // Now onClick only changes the selected index
                    // The LaunchedEffect above will handle the actual navigation
                    bottomBarState.selectTab(index)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColor.BrightGrey,
                    selectedTextColor = AppColor.BrightGrey,
                    unselectedIconColor = AppColor.BrightGrey.copy(alpha = 0.2f),
                    unselectedTextColor = AppColor.BrightGrey.copy(alpha = 0.2f),
                    indicatorColor = AppColor.DarkGrey
                ),
            )
        }
    }
}