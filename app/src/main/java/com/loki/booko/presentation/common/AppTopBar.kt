package com.loki.booko.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.loki.booko.presentation.navigation.NavGraph

@Composable
fun AppTopBar(
    title: String,
    navController: NavController,
    onFavoriteClicked: () -> Unit = {},
    onDeleteAllClicked: () -> Unit = {}
) {

    TopAppBar(
        modifier = Modifier.background(
            color = MaterialTheme.colors.surface
        )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {

            Text(
                text = title, fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.weight(1f))

            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = backStackEntry?.destination

            val searchDestination = currentDestination?.route == NavGraph.HomeScreen.route
            val favoriteDestination = currentDestination?.route == NavGraph.FavoriteScreen.route
            val bookDetailDestination = currentDestination?.route == NavGraph.BookDetailScreen.route

            if(searchDestination) {

                TopBarIcon(
                    onIconClicked = {
                        navController.navigate(NavGraph.SearchScreen.route)
                                    },
                    description = "Search",
                    icon = Icons.Default.Search
                )
            }

            if(favoriteDestination) {

                var menuExpanded by remember {
                    mutableStateOf(false)
                }

                Column {
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
                                navController.navigate(NavGraph.SearchScreen.route)
                            }
                        ) {
                            Text(text = "Search")
                        }

                        DropdownMenuItem(
                            onClick = {
                                onDeleteAllClicked()
                                menuExpanded = false
                            }
                        ) {
                            Text(
                                text = "Delete All",
                                color = MaterialTheme.colors.error
                            )
                        }
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
    }
}

@Composable
fun TopBarIcon(
    modifier: Modifier = Modifier,
    onIconClicked: () -> Unit,
    description: String,
    icon: ImageVector,
    tint: Color = MaterialTheme.colors.onSurface
) {

    IconButton(
        onClick = { onIconClicked() }
    ) {

        Icon(
            imageVector = icon,
            contentDescription = description,
            modifier = modifier
                .padding(4.dp),
            tint = tint
        )
    }

}