package iam.thevoid.noxml.demo.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import iam.thevoid.noxml.coroutines.extensions.view.addSetter
import io.reactivex.Flowable
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import kotlinx.coroutines.flow.Flow

fun ImageView.setImageUrl(image : Flowable<String>) =
    addSetter(image) { setImageUrl(it) }

fun ImageView.setImageUrl(image : Flow<String>) =
    addSetter(image) { setImageUrl(it) }

fun ImageView.setImageUrl(url : String?) {
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}