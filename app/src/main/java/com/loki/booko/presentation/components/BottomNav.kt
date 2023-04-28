package com.loki.booko.presentation.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.loki.booko.presentation.navigation.Screens

@Composable
fun BottomNav(
    modifier: Modifier = Modifier,
    navController: NavController,
    onItemClick: (BottomNavItem) -> Unit
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val bottomBarDestination = navItems.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.background
        ) {

            navItems.forEach { bottomNavItem ->

                val selected = bottomNavItem.route == backStackEntry?.destination?.route

                BottomNavigationItem(
                    selected = selected,
                    selectedContentColor = MaterialTheme.colors.onBackground,
                    unselectedContentColor = MaterialTheme.colors.primaryVariant,
                    onClick = { onItemClick(bottomNavItem) },
                    icon = {
                        Icon(
                            imageVector = bottomNavItem.icon,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = bottomNavItem.navTitle)
                    }
                )
            }
        }
    }
}


data class BottomNavItem(
    val icon: ImageVector,
    val navTitle: String,
    val route: String
)


val navItems = listOf(

    BottomNavItem(
        icon = Icons.Filled.Home,
        navTitle = "Home",
        route = Screens.HomeScreen.route
    ),
    BottomNavItem(
        icon = Icons.Filled.Favorite,
        navTitle = "Favorites",
        route = Screens.FavoriteScreen.route
    ),
    BottomNavItem(
        icon = Icons.Filled.Settings,
        navTitle = "Settings",
        route = Screens.SettingsScreen.route
    )
)