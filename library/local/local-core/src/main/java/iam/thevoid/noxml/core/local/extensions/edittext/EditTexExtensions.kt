package iam.thevoid.noxml.core.local.extensions.edittext

import android.widget.EditText
import androidx.annotation.StringRes
import iam.thevoid.ae.moveCursorToEnd
import iam.thevoid.ae.requestSoftInput
import iam.thevoid.noxml.core.local.extensions.textview.hasTextWatcherDelegate
import iam.thevoid.noxml.core.local.extensions.textview.setTextResource
import iam.thevoid.noxml.core.local.extensions.textview.setTextSilent

fun EditText.setText(text: CharSequence, moveCursorToEnd: Boolean) {
    if (hasTextWatcherDelegate()) {
        setTextSilent(text)
    } else {
        setText(text)
    }
    if (moveCursorToEnd) {
        post {
            moveCursorToEnd()
            requestSoftInput()
        }
    }
}

fun EditText.setTextResource(@StringRes text: Int, moveCursorToEnd: Boolean) {
    if (hasTextWatcherDelegate()) {
        setTextResource(text)
    } else {
        setText(text)
    }
    if (moveCursorToEnd) {
        post {
            moveCursorToEnd()
            requestSoftInput()
        }
    }
}