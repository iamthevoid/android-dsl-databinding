package iam.thevoid.noxml.design.extensions.tablayout

import android.content.res.ColorStateList
import androidx.annotation.ColorRes
import com.google.android.material.tabs.TabLayout
import iam.thevoid.ae.color
import iam.thevoid.noxml.util.Internals

var TabLayout.tabRippleColorResource: Int
    get() = Internals.noGetter()
    set(@ColorRes res) {
        tabRippleColor = ColorStateList.valueOf(color(res))
    }

