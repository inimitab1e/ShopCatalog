package com.example.shopcatalog.extensions

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect


fun <T> Flow<T>.launchWhenStarted(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launchWhenStarted {
        this@launchWhenStarted.collect()
    }
}

fun <T> Flow<T>.launchWhenResumed(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launchWhenResumed {
        this@launchWhenResumed.collect()
    }
}

fun String.addOne(): String {
    return (this.toInt() + 1).toString()
}

fun String.removeOne(): String {
    return if (this.toInt() - 1 >= 0) {
        (this.toInt() - 1).toString()
    } else {
        this
    }
}