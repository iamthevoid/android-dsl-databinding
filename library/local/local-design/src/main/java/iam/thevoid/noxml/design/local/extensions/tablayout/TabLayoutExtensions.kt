package iam.thevoid.noxml.design.local.extensions.tablayout

import com.google.android.material.tabs.TabLayout
import iam.thevoid.noxml.core.local.tools.lazyDelegate
import iam.thevoid.noxml.design.local.R
import iam.thevoid.noxml.design.local.delegate.OnTabSelectedListenerDelegate

val TabLayout.onTabSelectListener: OnTabSelectedListenerDelegate
    get() = lazyDelegate(
        R.id.onTabSelectListener,
        ::OnTabSelectedListenerDelegate,
        ::addOnTabSelectedListener
    )