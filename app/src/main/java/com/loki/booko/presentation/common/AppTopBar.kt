package com.loki.booko.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    onMoreIconClicked: () -> Unit = {}
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

                TopBarIcon(
                    onIconClicked = { onMoreIconClicked() },
                    description = "More",
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
    icon: ImageVector
) {

    IconButton(
        onClick = { onIconClicked() }
    ) {

        Icon(
            imageVector = icon,
            contentDescription = description,
            modifier = modifier
                .padding(4.dp),
            tint = MaterialTheme.colors.onSurface
        )
    }

}