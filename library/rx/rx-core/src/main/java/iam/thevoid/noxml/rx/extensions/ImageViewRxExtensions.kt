package iam.thevoid.noxml.rx.extensions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import io.reactivex.Flowable

fun ImageView.setImageBitmap(bitmap: Flowable<Bitmap>) =
    addSetter(bitmap) { setImageBitmap(it) }

fun ImageView.setImageDrawable(drawable: Flowable<Drawable>) =
    addSetter(drawable) { setImageDrawable(it) }

fun ImageView.setImageResource(resource: Flowable<Int>) =
    addSetter(resource) { setImageResource(it) }

fun ImageView.setImageURI(uri: Flowable<Uri>) =
    addSetter(uri) { setImageURI(it) }

@RequiresApi(Build.VERSION_CODES.M)
fun ImageView.setImageIcon(icon: Flowable<Icon>) =
    addSetter(icon) { setImageIcon(it) }