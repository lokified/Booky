package com.loki.booko.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.loki.booko.presentation.components.BottomNav
import com.loki.booko.presentation.navigation.Navigation
import com.loki.booko.presentation.theme.BookoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookoTheme {

                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomNav(
                            navController = navController,
                            onItemClick = { navController.navigate(it.route) }
                        )
                    }
                ) {
                    Navigation(
                        navController = navController
                    )
                }
            }
        }
    }
}
