package iam.thevoid.noxml.coroutines.extensions

import android.widget.ProgressBar
import iam.thevoid.noxml.coroutines.data.CoroutineInt
import kotlinx.coroutines.flow.Flow


fun ProgressBar.setProgress(progress: CoroutineInt) =
    addSetter(progress.observe()) { this@setProgress.progress = it }

fun ProgressBar.setProgress(progress: Flow<Int>) =
    addSetter(progress) { this@setProgress.progress = it }