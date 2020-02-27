package iam.thevoid.noxml.extensions

import android.widget.TextView
import iam.thevoid.noxml.R
import iam.thevoid.noxml.delegate.TextWatcherDelegate

/**
 * MAINTENANCE
 */

val TextView.textWatcher: TextWatcherDelegate
    get() = ((getTag(R.id.textWatcher) as? TextWatcherDelegate)
        ?: TextWatcherDelegate().also {
            setTag(R.id.textWatcher, it)
            addTextChangedListener(it)
        })