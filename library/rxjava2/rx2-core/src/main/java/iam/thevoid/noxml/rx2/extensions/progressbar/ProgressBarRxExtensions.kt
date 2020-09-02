package iam.thevoid.noxml.rx2.extensions.progressbar

import android.widget.ProgressBar
import io.reactivex.Flowable
import iam.thevoid.noxml.rx2.data.fields.RxInt
import iam.thevoid.noxml.rx2.extensions.view.addSetter

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun ProgressBar.setProgress(progress: RxInt) =
    addSetter(progress.observe()) { this@setProgress.progress = it }

fun ProgressBar.setProgress(progress: Flowable<Int>) =
    addSetter(progress) { this@setProgress.progress = it }