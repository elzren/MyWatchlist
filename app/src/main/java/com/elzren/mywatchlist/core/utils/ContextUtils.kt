package com.elzren.mywatchlist.core.utils

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import com.elzren.mywatchlist.R

object ContextUtils {
    fun Context.showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun Context.openActionView(uri: Uri) {
        try {
            Intent(Intent.ACTION_VIEW, uri).apply {
                startActivity(this)
            }
        } catch (_: ActivityNotFoundException) {
            showToast(getString(R.string.no_app_found_for_this_action))
        }
    }

    fun Context.copyToClipboard(text: String) {
        val clipboardService = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        clipboardService?.setPrimaryClip(ClipData.newPlainText("title", text))
        // Android 13+ has clipboard popups
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            showToast(getString(R.string.copied))
        }
    }
}