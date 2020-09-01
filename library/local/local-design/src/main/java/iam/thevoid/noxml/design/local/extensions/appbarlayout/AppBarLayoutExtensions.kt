package iam.thevoid.noxml.design.local.extensions.appbarlayout

import com.google.android.material.appbar.AppBarLayout
import iam.thevoid.noxml.design.local.R
import iam.thevoid.noxml.design.local.delegate.OnOffsetChangeListenerDelegate

val AppBarLayout.onOffsetChangedListener
    get() = (getTag(R.id.onOffsetChangedListenerDelegate) as? OnOffsetChangeListenerDelegate) ?:
    OnOffsetChangeListenerDelegate().also {
        setTag(R.id.onOffsetChangedListenerDelegate, this)
        addOnOffsetChangedListener(it)
    }