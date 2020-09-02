package iam.thevoid.noxml.core.local.extensions.textview

import android.widget.TextView
import iam.thevoid.noxml.core.local.R
import iam.thevoid.noxml.core.local.delegate.TextWatcherDelegate
import iam.thevoid.noxml.core.local.tools.lazyDelegate

val TextView.textWatcherDelegate: TextWatcherDelegate
    get() = lazyDelegate(R.id.textWatcher, ::TextWatcherDelegate, ::addTextChangedListener)