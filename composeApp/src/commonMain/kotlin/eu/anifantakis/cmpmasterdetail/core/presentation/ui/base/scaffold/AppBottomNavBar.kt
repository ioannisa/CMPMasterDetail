package eu.anifantakis.cmpmasterdetail.core.presentation.ui.base.scaffold

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import eu.anifantakis.cmpmasterdetail.app.NavGraph
import eu.anifantakis.cmpmasterdetail.core.presentation.AppColor
import eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.AppIcons
import eu.anifantakis.cmpmasterdetail.core.presentation.popAndNavigate

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector? = null,
    val destination : NavGraph = NavGraph.Setup
) {
    @Composable
    fun bottomNavigationItems() : List<BottomNavigationItem> {
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

    var navigationSelectedItem by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = AppColor.DarkGrey,
        contentColor = AppColor.BrightGrey,
        tonalElevation = 4.dp
    ) {
        BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->

            //iterating all items with their respective indexes
            NavigationBarItem(
                selected = index == navigationSelectedItem,
                label = {
                    Text(navigationItem.label)
                },
                icon = {
                    navigationItem.icon?.let { icon ->
                        Icon(imageVector = icon, contentDescription = navigationItem.label)
                    }
                },
                onClick = {
                    navigationSelectedItem = index
                    navController.graph.findStartDestination().route?.let {
                        navController.popAndNavigate(
                            popTo = it,
                            navigate = navigationItem.destination
                        )
                    }

//                    navigationSelectedItem = index
//                    navController.navigate(navigationItem.destination) {
//                        navController.graph.findStartDestination().route?.let {
//                            popUpTo(it) {
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
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