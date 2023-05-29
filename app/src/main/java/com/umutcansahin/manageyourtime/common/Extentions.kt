package com.umutcansahin.manageyourtime.common

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun View.showSnackBar(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Snackbar.make(this,message,duration).show()
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
        }.show()
}

fun <T> Fragment.collectFlow(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(state) {
            flow.collect {
                action(it)
            }
        }
    }
}

val String.Companion.EMPTY: String by lazy { "" }
val String.Companion.START_TIME: String by lazy { "0" }
val String.Companion.END_TIME: String by lazy { "99999" }
val Long.Companion.THOUSAND: Long by lazy { 1_000 }
val String.Companion.YES: String by lazy { "YES" }
val String.Companion.NO: String by lazy { "NO" }

fun Long.convertToMinuteAndSecond(): String {
    val minute = (this / 60_000).toInt()
    val second = ((this / 1_000) % 60).toInt()
    return String.format("%02d:%02d", minute, second)
}

fun String.convertToMillisecond(): Long {
    return this.toLong() * 60_000
}

fun Long.convertFromMillisecondToMinute(): String {
    return (this / 60_000).toString()
}

fun String.toIntAndCheckIfEqualsZero(): Boolean {
    return this.toInt() == 0
}