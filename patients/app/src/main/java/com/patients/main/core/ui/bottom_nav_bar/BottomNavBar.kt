package com.patients.main.core.ui.bottom_nav_bar

import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.patients.main.core.navigation.NavItem


//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    visibleScreens: List<String>,
    navItems: List<NavItem<ImageVector>>,
    onResetSearchFilter: (() -> Unit)? = null,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = visibleScreens.any{it == currentDestination?.route}
    if (bottomBarDestination) {
        NavigationBar(
            modifier = modifier
        ) {
            navItems.forEach { navItem ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                    onClick = {
                        navController.navigate(navItem.route) {
                            onResetSearchFilter?.invoke()
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = navItem.selectedIcon,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}