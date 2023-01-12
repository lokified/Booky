package com.loki.booko.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    title: String
) {

    TopAppBar(
        modifier = Modifier.background(
            color = MaterialTheme.colors.surface
        )
    ) {

        Text(
            text = title, fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colors.onSurface
        )
    }
}