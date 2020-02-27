package iam.thevoid.noxml.rx.ext

import android.graphics.Bitmap
import android.widget.ImageView
import io.reactivex.Flowable

fun ImageView.setImageBitmap(flowable: Flowable<Bitmap>) =
    addSetter(flowable) { setImageBitmap(it) }