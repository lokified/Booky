package com.loki.booko.presentation.settings

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.loki.booko.presentation.common.AppTopBar
import com.loki.booko.util.DownloadMedium
import com.loki.booko.util.getDirectoryPathFromUri
import com.loki.booko.util.getSelectedMedium

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel
) {

    val context = LocalContext.current

    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)

    val locationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data

            if (uri != null) {
                val location = getDirectoryPathFromUri(context, uri)
                viewModel.updateDownloadLocation(location!!)
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Settings",
                navController = navController
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            HeaderSection(
                title = "Downloads",
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )

            ContentSection(
                content = "Change Download location",
                subContent = "storage/phone/" + viewModel.downloadLocation.value,
                onSectionClick = { locationLauncher.launch(intent) },
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )


            var isDialogVisible by remember { mutableStateOf(false) }

            ContentSection(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                content = "Change Download Over",
                subContent = viewModel.downloadMedium.value,
                onSectionClick = { isDialogVisible = true }
            )

            if (isDialogVisible) {
                DownloadMediumPopUp(
                    value = viewModel.downloadMedium.value,
                    onDismiss = { isDialogVisible = false},
                    selectedValue = { viewModel.changeDownloadMedium(it) }
                )
            }
        }
    }
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    title: String
) {

    Box(modifier = modifier.fillMaxWidth()) {

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}

@Composable
fun DownloadMediumPopUp(
    modifier: Modifier = Modifier,
    value: String,
    onDismiss: () -> Unit,
    selectedValue: (DownloadMedium) -> Unit
) {

    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {

        var selected by rememberSaveable { mutableStateOf(value) }

        val medium = listOf("Cellular data", "Wifi", "Cellular data and Wifi")

        Card {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text(text = "Download over")

                medium.forEach {

                    Row(
                        verticalAlignment = CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selected = it

                                getSelectedMedium(
                                    value = it,
                                    selectedValue = { selectedValue(it) }
                                )
                            }
                    ) {
                        RadioButton(
                            selected = selected == it,
                            onClick = {
                                selected = it

                                getSelectedMedium(
                                    value = it,
                                    selectedValue = { selectedValue(it) }
                                )
                            },
                        )

                        Text(text = it)
                    }
                }
            }
        }
    }
}


@Composable
fun ContentSection(
    modifier: Modifier = Modifier,
    content: String,
    subContent: String = "",
    trailingIcon: ImageVector = Icons.Default.ArrowForwardIos,
    onSectionClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .clickable {
                onSectionClick()
            }
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = content)
                Text(
                    text = subContent,
                    fontSize = 12.sp
                )
            }


            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = trailingIcon,
                contentDescription = null
            )
        }
    }

}