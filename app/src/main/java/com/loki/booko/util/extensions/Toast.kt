package com.loki.booko.util.extensions

import android.app.Activity
import android.widget.Toast


fun Activity.showToast(content: String) {
    Toast.makeText(this, content, Toast.LENGTH_LONG).show()
}