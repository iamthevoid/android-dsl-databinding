package iam.thevoid.noxml.coroutines.extensions

import android.widget.EditText
import iam.thevoid.ae.*
import iam.thevoid.e.format
import iam.thevoid.noxml.coroutines.data.*
import iam.thevoid.noxml.rx.recycler.extensions.setTextResourceSilent
import iam.thevoid.noxml.rx.recycler.extensions.setTextSilent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * SETTER
 */

fun <T : CharSequence> EditText.setText(
    text: Flow<T>,
    moveCursorToEnd: Boolean = true
) = addSetter(text) { setTextSilent(it, moveCursorToEnd) }

fun <T : CharSequence> EditText.setText(
    charSequence: CoroutineCharSequence<T>,
    moveCursorToEnd: Boolean = true
) = setText(charSequence.observe(), moveCursorToEnd)

fun EditText.setText(text: CoroutineString, moveCursorToEnd: Boolean = true) =
    setText(text.observe(), moveCursorToEnd)

fun EditText.setText(text: CoroutineInt, moveCursorToEnd: Boolean = true) =
    setText(text.observe().map { "$it" }, moveCursorToEnd)

fun EditText.setText(text: CoroutineLong, moveCursorToEnd: Boolean = true) =
    setText(text.observe().map { "$it" }, moveCursorToEnd)

fun EditText.setText(text: CoroutineFloat, precision: Int? = null, moveCursorToEnd: Boolean = true) =
    setText(text.observe().map { it.format(precision) }, moveCursorToEnd)

fun EditText.setText(text: CoroutineDouble, precision: Int? = null, moveCursorToEnd: Boolean = true) =
    setText(text.observe().map { it.format(precision) }, moveCursorToEnd)

fun EditText.setTextResource(textResource: Flow<Int>, moveCursorToEnd: Boolean) =
    addSetter(textResource) { setTextResourceSilent(it, moveCursorToEnd) }

fun EditText.setTextResource(textResource: CoroutineInt, moveCursorToEnd: Boolean = true) =
    setTextResource(textResource.observe(), moveCursorToEnd)

fun EditText.setTextColor(color: Flow<Int>) =
    addSetter(color) { setTextColor(it) }

fun EditText.setHintColor(color: Flow<Int>) =
    addSetter(color) { setHintTextColor(it) }

fun EditText.setTextColorResource(colorResource: Flow<Int>) =
    addSetter(colorResource) { setTextColor(color(it)) }

fun EditText.setRequestInput(requestInput: CoroutineBoolean) =
    setRequestInput(requestInput.observe())

fun EditText.setRequestInput(requestInput: Flow<Boolean>) =
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
