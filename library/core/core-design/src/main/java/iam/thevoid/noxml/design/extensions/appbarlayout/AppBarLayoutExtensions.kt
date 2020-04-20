package iam.thevoid.noxml.design.extensions.appbarlayout

import com.google.android.material.appbar.AppBarLayout
import iam.thevoid.noxml.design.R
import iam.thevoid.noxml.design.delegate.OnOffsetChangeListenerDelegate

val scrollFlagScroll
    get() = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL

val scrollFlagSnap
    get() = AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP

val scrollFlagSnapMargins
    get() = AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP_MARGINS

val scrollFlagEnterAlways
    get() = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS

val scrollFlagEnterAlwaysCollapsed
    get() = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED

val scrollFlagExitUntilCollapsed
    get() = AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED

val AppBarLayout.onOffsetChangedListener
    get() = (getTag(R.id.onOffsetChangedListenerDelegate) as? OnOffsetChangeListenerDelegate) ?:
    OnOffsetChangeListenerDelegate().also {
        setTag(R.id.onOffsetChangedListenerDelegate, this)
        addOnOffsetChangedListener(it)
    }