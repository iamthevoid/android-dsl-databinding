@file:Suppress("unused")

package iam.thevoid.noxml.rx2.extensions.progressbar

import android.widget.ProgressBar
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import io.reactivex.Flowable

fun ProgressBar.setProgress(progress: Flowable<Int>) =
    addSetter(progress) { this@setProgress.progress = it }