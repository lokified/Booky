package com.loki.booko.util

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import java.util.Locale

fun getDirectoryPathFromUri(context: Context, uri: Uri): String? {
    val document = DocumentFile.fromTreeUri(context, uri)
    return document?.let { documentFile ->
        val pathSegments = mutableListOf<String>()
        var currentDocument: DocumentFile? = documentFile

        while (currentDocument != null) {
            pathSegments.add(currentDocument.name ?: return null)
            currentDocument = currentDocument.parentFile
        }

        val reversedPathSegments = pathSegments.reversed()
        return@let reversedPathSegments.joinToString("/")
    }
}

@RequiresApi(Build.VERSION_CODES.S)
fun defaultDirectory(location: String): String {

    var selectedLocation = ""

    val directories = listOf(
        Environment.DIRECTORY_ALARMS,
        Environment.DIRECTORY_AUDIOBOOKS,
        Environment.DIRECTORY_DCIM,
        Environment.DIRECTORY_MOVIES,
        Environment.DIRECTORY_DOCUMENTS,
        Environment.DIRECTORY_DOWNLOADS,
        Environment.DIRECTORY_MUSIC,
        Environment.DIRECTORY_NOTIFICATIONS,
        Environment.DIRECTORY_PICTURES,
        Environment.DIRECTORY_PODCASTS,
        Environment.DIRECTORY_RINGTONES,
    )

    for (directory in directories) {

        if (directory.lowercase(Locale.ROOT).contains(location.lowercase(Locale.ROOT))) {
            selectedLocation = directory
        }
        else {
            selectedLocation = Environment.DIRECTORY_DOWNLOADS
        }
    }

    return selectedLocation
}