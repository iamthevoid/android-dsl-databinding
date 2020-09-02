package iam.thevoid.noxml.design.local.extensions.appbarlayout

import com.google.android.material.appbar.AppBarLayout
import iam.thevoid.noxml.core.local.tools.lazyDelegate
import iam.thevoid.noxml.design.local.R
import iam.thevoid.noxml.design.local.delegate.OnOffsetChangeListenerDelegate

val AppBarLayout.onOffsetChangedListener
    get() = lazyDelegate(
        R.id.onOffsetChangedListenerDelegate,
        ::OnOffsetChangeListenerDelegate,
        ::addOnOffsetChangedListener
    )