package iam.thevoid.noxml.design.extensions

import com.google.android.material.tabs.TabLayout
import iam.thevoid.noxml.design.R
import iam.thevoid.noxml.design.delegate.OnTabSelectedListenerDelegate

val TabLayout.onTabSelectListener: OnTabSelectedListenerDelegate
    get() = ((getTag(R.id.onTabSelectListener) as? OnTabSelectedListenerDelegate)
        ?: OnTabSelectedListenerDelegate().also {
            setTag(R.id.onTabSelectListener, it)
            addOnTabSelectedListener(it)
        })