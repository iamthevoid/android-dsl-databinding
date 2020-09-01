package iam.thevoid.noxml.design.local.extensions.tablayout

import com.google.android.material.tabs.TabLayout
import iam.thevoid.noxml.design.local.R
import iam.thevoid.noxml.design.local.delegate.OnTabSelectedListenerDelegate

val TabLayout.onTabSelectListener: OnTabSelectedListenerDelegate
    get() = ((getTag(R.id.onTabSelectListener) as? OnTabSelectedListenerDelegate)
        ?: OnTabSelectedListenerDelegate().also {
            setTag(R.id.onTabSelectListener, it)
            addOnTabSelectedListener(it)
        })