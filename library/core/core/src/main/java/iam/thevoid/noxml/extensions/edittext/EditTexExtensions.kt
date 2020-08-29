package iam.thevoid.noxml.extensions.edittext

import android.widget.EditText
import androidx.annotation.StringRes
import iam.thevoid.ae.moveCursorToEnd
import iam.thevoid.ae.requestSoftInput
import iam.thevoid.ae.string
import iam.thevoid.noxml.extensions.textview.textWatcherDelegate

fun EditText.setTextSilent(text: CharSequence, moveCursorToEnd: Boolean = true) {
    if (text.toString() == this.text.toString())
        return
    val watcher = textWatcherDelegate
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
    val watcher = textWatcherDelegate
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