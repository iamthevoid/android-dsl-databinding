package iam.thevoid.noxml.design.extensions.tablayout

import android.content.res.ColorStateList
import androidx.annotation.ColorRes
import com.google.android.material.tabs.TabLayout
import iam.thevoid.ae.color
import iam.thevoid.noxml.design.R
import iam.thevoid.noxml.design.delegate.OnTabSelectedListenerDelegate
import iam.thevoid.noxml.util.Internals

var TabLayout.tabRippleColorResource: Int
    get() = Internals.noGetter()
    set(@ColorRes res) {
        tabRippleColor = ColorStateList.valueOf(color(res))
    }

val TabLayout.onTabSelectListener: OnTabSelectedListenerDelegate
    get() = ((getTag(R.id.onTabSelectListener) as? OnTabSelectedListenerDelegate)
        ?: OnTabSelectedListenerDelegate().also {
            setTag(R.id.onTabSelectListener, it)
            addOnTabSelectedListener(it)
        })