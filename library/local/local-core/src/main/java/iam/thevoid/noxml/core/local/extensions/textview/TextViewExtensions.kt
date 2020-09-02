package iam.thevoid.noxml.core.local.extensions.textview

import android.widget.TextView
import androidx.annotation.StringRes
import iam.thevoid.ae.string
import iam.thevoid.noxml.core.local.R
import iam.thevoid.noxml.core.local.delegate.TextWatcherDelegate
import iam.thevoid.noxml.core.local.tools.isLazyDelegateAttached
import iam.thevoid.noxml.core.local.tools.lazyDelegate

val TextView.textWatcherDelegate: TextWatcherDelegate
    get() = lazyDelegate(R.id.textWatcher, ::TextWatcherDelegate, ::addTextChangedListener)

fun TextView.hasTextWatcherDelegate() = isLazyDelegateAttached(R.id.textWatcher)

fun TextView.setTextSilent(text: CharSequence) {
    if (text.toString() == this.text.toString())
        return
    val watcher = textWatcherDelegate
    removeTextChangedListener(watcher)
    setText(text)
    addTextChangedListener(watcher)
}

fun TextView.setTextResource(@StringRes text: Int) {
    if (string(text) == "${this.text}")
        return
    val watcher = textWatcherDelegate
    removeTextChangedListener(watcher)
    setText(text)
    addTextChangedListener(watcher)
}