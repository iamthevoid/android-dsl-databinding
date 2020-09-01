package iam.thevoid.noxml.core.local.extensions.textview

import android.widget.TextView
import iam.thevoid.noxml.core.local.R
import iam.thevoid.noxml.core.local.delegate.TextWatcherDelegate

val TextView.textWatcherDelegate: TextWatcherDelegate
    get() = ((getTag(R.id.textWatcher) as? TextWatcherDelegate)
        ?: TextWatcherDelegate().also {
            setTag(R.id.textWatcher, it)
            addTextChangedListener(it)
        })