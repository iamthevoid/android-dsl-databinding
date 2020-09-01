package iam.thevoid.noxml.rx2.extensions.edittext

import android.widget.EditText
import iam.thevoid.ae.color
import iam.thevoid.ae.requestSoftInput
import iam.thevoid.ae.resetFocus
import iam.thevoid.e.format
import iam.thevoid.noxml.rx2.data.fields.*
import iam.thevoid.noxml.core.local.extensions.edittext.setTextResourceSilent
import iam.thevoid.noxml.core.local.extensions.edittext.setTextSilent
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import io.reactivex.Flowable

/**
 * SETTER
 */

fun <T : CharSequence> EditText.setText(text: Flowable<T>, moveCursorToEnd: Boolean = true) =
    addSetter(text) { setTextSilent(it, moveCursorToEnd) }

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun <T : CharSequence> EditText.setText(text: RxCharSequence<T>, moveCursorToEnd: Boolean = true) =
    setText(text.observe(), moveCursorToEnd)

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun EditText.setText(text: RxInt, moveCursorToEnd: Boolean = true) =
    setText(text.observe().map { "$it" }, moveCursorToEnd)

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun EditText.setText(text: RxLong, moveCursorToEnd: Boolean = true) =
    setText(text.observe().map { "$it" }, moveCursorToEnd)

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun EditText.setText(text: RxFloat, precision: Int? = null, moveCursorToEnd: Boolean = true) =
    setText(text.observe().map { it.format(precision) }, moveCursorToEnd)

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun EditText.setText(text: RxDouble, precision: Int? = null, moveCursorToEnd: Boolean = true) =
    setText(text.observe().map { it.format(precision) }, moveCursorToEnd)

fun EditText.setTextResource(textResource: Flowable<Int>, moveCursorToEnd: Boolean) =
    addSetter(textResource) { setTextResourceSilent(it, moveCursorToEnd) }

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun EditText.setTextResource(textResource: RxInt, moveCursorToEnd: Boolean = true) =
    setTextResource(textResource.observe(), moveCursorToEnd)

fun EditText.setTextColor(color: Flowable<Int>) =
    addSetter(color) { setTextColor(it) }

fun EditText.setHintColor(color: Flowable<Int>) =
    addSetter(color) { setHintTextColor(it) }

fun EditText.setTextColorResource(colorResource: Flowable<Int>) =
    addSetter(colorResource) { setTextColor(color(it)) }

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun EditText.setRequestInput(boolean: RxBoolean) =
    setRequestInput(boolean.observe())

fun EditText.setSelection(selection: Flowable<Int>) =
    addSetter(selection) { setSelection(it) }

fun EditText.setRequestInput(requestInput: Flowable<Boolean>) =
    addSetter(requestInput) {
        val focusListener = onFocusChangeListener
        onFocusChangeListener = null
        if (it) {
            post {
                requestSoftInput()
                onFocusChangeListener = focusListener
            }
        } else {
            resetFocus()
            onFocusChangeListener = focusListener
        }
    }