package thevoid.iam.rx.widget.ext

import android.widget.TextView
import iam.thevoid.ae.color
import iam.thevoid.ae.setTextStrikeThru
import iam.thevoid.ae.string
import iam.thevoid.e.format
import io.reactivex.Flowable
import thevoid.iam.rx.rxdata.fields.*

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
    addSetter(rxIntResource.observe()) { text = string(it) }

fun TextView.setTextResource(textResourceFlowable: Flowable<Int>) =
    addSetter(textResourceFlowable) { text = string(it) }

fun TextView.setTextColor(colorFlowable: Flowable<Int>) =
    addSetter(colorFlowable) { setTextColor(it) }

fun TextView.setTextColorResourse(colorResFlowable: Flowable<Int>) =
    addSetter(colorResFlowable) { setTextColor(color(it)) }

fun TextView.setHintTextColor(colorResFlowable: Flowable<Int>) =
    addSetter(colorResFlowable) { setHintTextColor(it) }

fun TextView.setHintTextColorResourse(colorResFlowable: Flowable<Int>) =
    addSetter(colorResFlowable) { setHintTextColor(color(it)) }

fun TextView.setTextStrikeThru(strikeThru: Flowable<Boolean>) =
    addSetter(strikeThru) { setTextStrikeThru(it) }