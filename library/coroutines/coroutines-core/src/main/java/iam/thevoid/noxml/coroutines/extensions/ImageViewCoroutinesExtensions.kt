package iam.thevoid.noxml.coroutines.extensions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build.VERSION_CODES.M
import android.widget.ImageView
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow

fun ImageView.setImageBitmap(bitmap: Flow<Bitmap>) =
    addSetter(bitmap) { setImageBitmap(it) }

fun ImageView.setImageDrawable(drawable: Flow<Drawable>) =
    addSetter(drawable) { setImageDrawable(it) }

fun ImageView.setImageResource(resource: Flow<Int>) =
    addSetter(resource) { setImageResource(it) }

fun ImageView.setImageURI(uri: Flow<Uri>) =
    addSetter(uri) { setImageURI(it) }

@RequiresApi(M)
fun ImageView.setImageIcon(icon: Flow<Icon>) =
    addSetter(icon) { setImageIcon(it) }