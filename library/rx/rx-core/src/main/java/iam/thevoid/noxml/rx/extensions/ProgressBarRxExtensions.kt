package iam.thevoid.noxml.rx.extensions

import android.widget.ProgressBar
import io.reactivex.Flowable
import iam.thevoid.noxml.rx.data.fields.RxInt


fun ProgressBar.setProgress(progress: RxInt) =
    addSetter(progress.observe()) { this@setProgress.progress = it }

fun ProgressBar.setProgress(progress: Flowable<Int>) =
    addSetter(progress) { this@setProgress.progress = it }