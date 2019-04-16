package thevoid.iam.components

import android.widget.TextView
import io.reactivex.Flowable
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textColorResource
import org.jetbrains.anko.textResource

fun <T : CharSequence> TextView.setText(textFlowable: Flowable<T>) =
    addSetter(textFlowable) { text = it }

fun TextView.setTextResource(textResourceFlowable: Flowable<Int>) =
    addSetter(textResourceFlowable) { textResource = it }

fun TextView.setTextColor(colorFlowable: Flowable<Int>) =
    addSetter(colorFlowable) { textColor = it }

fun TextView.setTextColorResourse(colorResFlowable: Flowable<Int>) =
    addSetter(colorResFlowable) { textColorResource = it }