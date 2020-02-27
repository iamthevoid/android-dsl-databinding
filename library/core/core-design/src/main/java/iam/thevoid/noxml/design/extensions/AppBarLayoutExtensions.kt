package iam.thevoid.noxml.design.extensions

import com.google.android.material.appbar.AppBarLayout
import iam.thevoid.noxml.design.R
import iam.thevoid.noxml.design.delegate.OnOffsetChangeListenerDelegate

val AppBarLayout.onOffsetChangedListener
    get() = (getTag(R.id.onOffsetChangedListenerDelegate) as? OnOffsetChangeListenerDelegate) ?:
    OnOffsetChangeListenerDelegate().also {
        setTag(R.id.onOffsetChangedListenerDelegate, this)
        addOnOffsetChangedListener(it)
    }