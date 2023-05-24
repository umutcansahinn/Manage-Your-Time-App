package com.umutcansahin.manageyourtime.common

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun Context.showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
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
        .setPositiveButton("Yes") { _, _ ->
            positiveButton.invoke()
        }
        .setCancelable(true)
        .setNegativeButton("No") { dialog, _ ->
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