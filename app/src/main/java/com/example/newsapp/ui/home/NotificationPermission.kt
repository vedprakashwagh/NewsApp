package com.example.newsapp.ui.home

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.utils.didShowNotificationRationale
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermission(rgbColor: Color, bodyTextColor: Color) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val notificationPermissionState =
            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
        val shouldShowPermissionDialog = rememberSaveable { mutableStateOf(false) }
        val context = LocalContext.current

        LaunchedEffect(key1 = Unit, block = {
            val didShowPermissionRationale = context.didShowNotificationRationale

            if (notificationPermissionState.status.shouldShowRationale || didShowPermissionRationale) {
                shouldShowPermissionDialog.value = true
            }
        })

        when (notificationPermissionState.status) {
            is PermissionStatus.Denied -> {
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
                        .clickable {
                            val settingsIntent: Intent =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)

                                } else {
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                                }
                            context.startActivity(settingsIntent)
                        }
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.error)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Notifications are disabled! Please enable them.",
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onError
                    )

                    IconButton(
                        onClick = {
                            val settingsIntent: Intent =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)

                                } else {
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                                }
                            context.startActivity(settingsIntent)
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.onError,
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsOff,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                if (shouldShowPermissionDialog.value) {
                    AlertDialog(
                        onDismissRequest = { shouldShowPermissionDialog.value = false },
                        title = {
                            Text(
                                text = "Stay connected with the world",
                                color = bodyTextColor
                            )
                        },
                        text = {
                            Text(
                                text = "We never send unnecessary notifications. Please enable them to receive superfast updates about the latest headlines from all over the world!",
                                color = bodyTextColor
                            )
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                notificationPermissionState.launchPermissionRequest()
                                context.didShowNotificationRationale = false
                            }) {
                                Text(
                                    text = "Ok", color = bodyTextColor
                                )
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                shouldShowPermissionDialog.value = false
                                context.didShowNotificationRationale = false
                            }) {
                                Text(
                                    text = "Cancel",
                                    color = bodyTextColor
                                )
                            }
                        },
                        containerColor = rgbColor,
                        titleContentColor = bodyTextColor,
                        textContentColor = bodyTextColor,
                    )
                }
            }

            PermissionStatus.Granted -> {

            }
        }
    }
}