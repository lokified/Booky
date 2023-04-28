package com.loki.booko.presentation.settings

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.loki.booko.presentation.common.AppTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavController
) {


    Scaffold(
        topBar = {
            AppTopBar(
                title = "Settings",
                navController = navController
            )
        }
    ) {


    }
}