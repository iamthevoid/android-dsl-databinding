package thevoid.iam.components.widget.ext

import android.widget.TextView
import iam.thevoid.e.format
import io.reactivex.Flowable
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textColorResource
import org.jetbrains.anko.textResource
import thevoid.iam.components.rx.fields.*

fun <T : CharSequence> TextView.setText(rxString: RxCharSequence<T>) =
    addSetter(rxString.observe()) { text = it }

fun <T : CharSequence> TextView.setText(textFlowable: Flowable<T>) =
    addSetter(textFlowable) { text = it }

fun TextView.setText(rxInt: RxInt) =
    addSetter(rxInt.observe()) { text = "$it" }

fun TextView.setText(rxLong: RxLong) =
    addSetter(rxLong.observe()) { text = "$it" }

fun TextView.setText(rxFloat: RxFloat, precision: Int? = null) =
    addSetter(rxFloat.observe()) { text = it.format(precision) }

fun TextView.setText(rxDouble: RxDouble, precision: Int? = null) =
    addSetter(rxDouble.observe()) { text = it.format(precision) }

fun TextView.setTextResource(rxIntResource: RxInt) =
    addSetter(rxIntResource.observe()) { textResource = it }

fun TextView.setTextResource(textResourceFlowable: Flowable<Int>) =
    addSetter(textResourceFlowable) { textResource = it }

fun TextView.setTextColor(colorFlowable: Flowable<Int>) =
    addSetter(colorFlowable) { textColor = it }

fun TextView.setTextColorResourse(colorResFlowable: Flowable<Int>) =
    addSetter(colorResFlowable) { textColorResource = it }