package com.umutcansahin.manageyourtime.common.extensions

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.umutcansahin.manageyourtime.R

fun View.showSnackBar(message: String, view: View = this, duration: Int = Toast.LENGTH_SHORT) {
    Snackbar.make(view, message, duration).show()
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.showAlertDialog(
    title: String,
    message: String,
    positiveButton: (() -> Unit)
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(String.YES) { _, _ ->
            positiveButton.invoke()
        }
        .setCancelable(true)
        .setNegativeButton(String.NO) { dialog, _ ->
            dialog.cancel()
        }
        .show()
}

@SuppressLint("UseCompatLoadingForDrawables")
fun Context.showMaterialAlertDialog(
    view:View,
) {
    MaterialAlertDialogBuilder(this)
        .setView(view)
        .show()
}