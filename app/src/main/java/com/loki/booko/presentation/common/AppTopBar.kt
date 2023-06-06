package com.loki.booko.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.loki.booko.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    navController: NavController,
    onFavoriteClicked: () -> Unit = {},
    onDeleteAllClicked: () -> Unit = {}
) {

    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
            ) },
        actions = {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = backStackEntry?.destination

            val searchDestination = currentDestination?.route == Screens.HomeScreen.route
            val favoriteDestination = currentDestination?.route == Screens.FavoriteScreen.route
            val bookDetailDestination = currentDestination?.route == Screens.BookDetailScreen.route

            if(searchDestination) {

                TopBarIcon(
                    onIconClicked = {
                        navController.navigate(Screens.SearchScreen.route)
                    },
                    description = "Search",
                    icon = Icons.Default.Search
                )
            }

            if(favoriteDestination) {

                var menuExpanded by remember {
                    mutableStateOf(false)
                }

                Box {
                    TopBarIcon(
                        onIconClicked = { menuExpanded = !menuExpanded},
                        description = "More",
                        icon = Icons.Default.MoreVert
                    )

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {

                        DropdownMenuItem(
                            onClick = {
                                navController.navigate(Screens.SearchScreen.route)
                            },
                            text = {
                                Text(text = "Search")
                            }
                        )

                        DropdownMenuItem(
                            onClick = {
                                onDeleteAllClicked()
                                menuExpanded = false
                            },
                            text = {
                                Text(
                                    text = "Delete All",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        )
                    }
                }
            }

            if (bookDetailDestination) {

                TopBarIcon(
                    onIconClicked = { onFavoriteClicked() },
                    description = "Favorite",
                    icon = Icons.Default.MoreVert
                )
            }
        }
    )
}

@Composable
fun TopBarIcon(
    modifier: Modifier = Modifier,
    onIconClicked: () -> Unit,
    description: String,
    icon: ImageVector,
) {

    IconButton(
        onClick = { onIconClicked() }
    ) {

        Icon(
            imageVector = icon,
            contentDescription = description,
            modifier = modifier
                .padding(4.dp),
        )
    }
}