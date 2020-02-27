package iam.thevoid.noxml.coroutines.extensions

import android.widget.EditText
import androidx.annotation.StringRes
import iam.thevoid.ae.*
import iam.thevoid.e.format
import iam.thevoid.noxml.coroutines.fields.*
import iam.thevoid.noxml.extensions.textWatcher
import kotlinx.coroutines.flow.Flow

/**
 * SETTER
 */

fun EditText.setTextSilent(text: CharSequence, moveCursorToEnd: Boolean = true) {
    if (text.toString() == this.text.toString())
        return
    val watcher = textWatcher
    removeTextChangedListener(watcher)
    setText(text)
    addTextChangedListener(watcher)
    if (moveCursorToEnd) {
        post {
            moveCursorToEnd()
            requestSoftInput()
        }
    }
}

fun EditText.setTextResourceSilent(@StringRes text: Int, moveCursorToEnd: Boolean = true) {
    if (string(text) == "${this.text}")
        return
    val watcher = textWatcher
    removeTextChangedListener(watcher)
    setText(text)
    addTextChangedListener(watcher)
    if (moveCursorToEnd) {
        post {
            moveCursorToEnd()
            requestSoftInput()
        }
    }
}

fun <T : CharSequence> EditText.setText(
    charSequence: CoroutineCharSequence<T>,
    moveCursorToEnd: Boolean = true
) = addSetter(charSequence.observe()) { setTextSilent(it, moveCursorToEnd) }

fun <T : CharSequence> EditText.setText(
    textFlow: Flow<T>,
    moveCursorToEnd: Boolean = true
) = addSetter(textFlow) { setTextSilent(it, moveCursorToEnd) }

fun EditText.setText(string: CoroutineString, moveCursorToEnd: Boolean = true) =
    addSetter(string.observe()) { setTextSilent(it, moveCursorToEnd) }

fun EditText.setText(int: CoroutineInt, moveCursorToEnd: Boolean = true) =
    addSetter(int.observe()) { setTextSilent("$it", moveCursorToEnd) }

fun EditText.setText(long: CoroutineLong, moveCursorToEnd: Boolean = true) =
    addSetter(long.observe()) { setTextSilent("$it", moveCursorToEnd) }

fun EditText.setText(float: CoroutineFloat, precision: Int? = null, moveCursorToEnd: Boolean = true) =
    addSetter(float.observe()) { setTextSilent(it.format(precision), moveCursorToEnd) }

fun EditText.setText(double: CoroutineDouble, precision: Int? = null, moveCursorToEnd: Boolean = true) =
    addSetter(double.observe()) { setTextSilent(it.format(precision), moveCursorToEnd) }

fun EditText.setTextResource(resource: CoroutineInt, moveCursorToEnd: Boolean = true) =
    addSetter(resource.observe()) { setTextResourceSilent(it, moveCursorToEnd) }

fun EditText.setTextResource(textResourceFlow: Flow<Int>, moveCursorToEnd: Boolean) =
    addSetter(textResourceFlow) { setTextResourceSilent(it, moveCursorToEnd) }

fun EditText.setTextColor(colorFlow: Flow<Int>) =
    addSetter(colorFlow) { setTextColor(it) }

fun EditText.setHintColor(colorFlow: Flow<Int>) =
    addSetter(colorFlow) { setHintTextColor(it) }

fun EditText.setTextColorResource(colorResourceFlow: Flow<Int>) =
    addSetter(colorResourceFlow) { setTextColor(color(it)) }

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
