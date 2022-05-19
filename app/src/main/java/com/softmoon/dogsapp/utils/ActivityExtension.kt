package com.softmoon.dogsapp.utils

import android.app.Activity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.softmoon.dogsapp.R

fun Activity.handleError(fail: Resource.Failure, retry: (() -> Unit)?  = null) {
    if (fail.isNetworkError) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.attention)
            .setMessage(R.string.error_data)
            .setPositiveButton(R.string.button_retry) { dialog, _ ->
                retry?.invoke()
                dialog.dismiss()
            }
            .create()
            .show()
    } else {
        val builder = MaterialAlertDialogBuilder(this)

        when (fail.errorCode) {
            500 -> {
                builder
                    .setTitle(R.string.error)
                    .setMessage(R.string.error_server)
                    .setPositiveButton(R.string.button_retry) { dialog, _ ->
                        retry?.invoke()
                        dialog.dismiss()
                    }
            }
            else -> {
                builder
                    .setTitle(R.string.error)
                    .setMessage(R.string.error_unexpected)
                    .setPositiveButton(R.string.button_retry) { dialog, _ ->
                        retry?.invoke()
                        dialog.dismiss()
                    }
            }
        }

        builder
            .create()
            .show()
    }
}