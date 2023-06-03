package com.loki.booko.util

enum class DownloadMedium {
    WIFI,
    CELLULAR,
    BOTH;

    fun toPreferenceString(): String {
        return when (this) {
            CELLULAR -> "Cellular data"
            WIFI -> "Wifi"
            BOTH -> "Cellular data and Wifi"
        }
    }
}

fun getSelectedMedium(value: String, selectedValue: (DownloadMedium) -> Unit ) {
    when(value) {
        "Cellular data" -> selectedValue(DownloadMedium.CELLULAR)
        "Wifi" -> selectedValue(DownloadMedium.WIFI)
        "Cellular data and Wifi" -> selectedValue(DownloadMedium.BOTH)
    }
}