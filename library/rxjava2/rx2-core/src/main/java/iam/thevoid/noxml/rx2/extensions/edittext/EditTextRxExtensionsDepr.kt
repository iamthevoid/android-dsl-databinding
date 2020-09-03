package iam.thevoid.noxml.rx2.extensions.edittext

import android.widget.EditText
import iam.thevoid.e.format
import iam.thevoid.noxml.rx2.data.fields.*

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun <T : CharSequence> EditText.setText(text: RxCharSequence<T>, moveCursorToEnd: Boolean = true) =
    setText(text.observe(), moveCursorToEnd)

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun EditText.setText(text: RxInt, moveCursorToEnd: Boolean = true) =
    setText(text.observe().map { "$it" }, moveCursorToEnd)

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun EditText.setText(text: RxLong, moveCursorToEnd: Boolean = true) =
    setText(text.observe().map { "$it" }, moveCursorToEnd)

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun EditText.setText(text: RxFloat, precision: Int? = null, moveCursorToEnd: Boolean = true) =
    setText(text.observe().map { it.format(precision) }, moveCursorToEnd)

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun EditText.setText(text: RxDouble, precision: Int? = null, moveCursorToEnd: Boolean = true) =
    setText(text.observe().map { it.format(precision) }, moveCursorToEnd)

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun EditText.setTextResource(textResource: RxInt, moveCursorToEnd: Boolean = true) =
    setTextResource(textResource.observe(), moveCursorToEnd)

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun EditText.setRequestInput(boolean: RxBoolean) =
    setRequestInput(boolean.observe())