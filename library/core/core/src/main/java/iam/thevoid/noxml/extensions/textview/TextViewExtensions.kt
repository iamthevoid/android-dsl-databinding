package iam.thevoid.noxml.extensions.textview

import android.os.Build
import android.text.TextUtils
import android.widget.TextView
import iam.thevoid.noxml.R
import iam.thevoid.noxml.delegate.TextWatcherDelegate
import iam.thevoid.noxml.util.Internals

// Constants

val ellipsizeNone
    get() = null

val ellipsizeMiddle
    get() = TextUtils.TruncateAt.MIDDLE

val ellipsizeStart
    get() = TextUtils.TruncateAt.START

val ellipsizeEnd
    get() = TextUtils.TruncateAt.END

val ellipsizeMarquee
    get() = TextUtils.TruncateAt.MARQUEE


var TextView.safeLineHeight: Int
    get() = Internals.noGetter()
    set(value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lineHeight = value
        } else {
            setLineSpacing(value - textSize, 1f)
        }
    }


val TextView.textWatcherDelegate: TextWatcherDelegate
    get() = ((getTag(R.id.textWatcher) as? TextWatcherDelegate)
        ?: TextWatcherDelegate().also {
            setTag(R.id.textWatcher, it)
            addTextChangedListener(it)
        })