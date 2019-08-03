package thevoid.iam.ankoobservablecomponents.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import io.reactivex.Flowable
import thevoid.iam.rx.widget.ext.addSetter

fun ImageView.setImageUrl(image : Flowable<String>) =
    addSetter(image) { setImageUrl(it) }

fun ImageView.setImageUrl(url : String?) {
    Glide.with(this)
        .load(url)
        .dontAnimate()
        .into(this)
}