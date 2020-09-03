@file:Suppress("unused")

package iam.thevoid.noxml.rx2.extensions.edittext

import android.widget.EditText
import iam.thevoid.ae.color
import iam.thevoid.ae.requestSoftInput
import iam.thevoid.ae.resetFocus
import iam.thevoid.noxml.core.local.extensions.edittext.setText
import iam.thevoid.noxml.core.local.extensions.edittext.setTextResource
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import io.reactivex.Flowable

/**
 * SETTER
 */

fun <T : CharSequence> EditText.setText(text: Flowable<T>, moveCursorToEnd: Boolean = true) =
    addSetter(text) { setText(it, moveCursorToEnd) }

fun EditText.setTextResource(textResource: Flowable<Int>, moveCursorToEnd: Boolean) =
    addSetter(textResource) { setTextResource(it, moveCursorToEnd) }

fun EditText.setTextColor(color: Flowable<Int>) =
    addSetter(color) { setTextColor(it) }

fun EditText.setHintColor(color: Flowable<Int>) =
    addSetter(color) { setHintTextColor(it) }

fun EditText.setTextColorResource(colorResource: Flowable<Int>) =
    addSetter(colorResource) { setTextColor(color(it)) }

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