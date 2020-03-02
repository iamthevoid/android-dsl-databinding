package iam.thevoid.noxml.rx.recycler.extensions

import android.widget.EditText
import androidx.annotation.StringRes
import iam.thevoid.ae.moveCursorToEnd
import iam.thevoid.ae.requestSoftInput
import iam.thevoid.ae.string

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