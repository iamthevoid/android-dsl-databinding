@file:Suppress("unused")

package iam.thevoid.noxml.core.local.extensions.viewpager

import androidx.viewpager.widget.ViewPager
import iam.thevoid.noxml.core.local.R
import iam.thevoid.noxml.core.local.delegate.OnPageChangeDelegate
import iam.thevoid.noxml.core.local.tools.lazyDelegate

val ViewPager.onPageChangeListener: OnPageChangeDelegate
    get() = lazyDelegate(R.id.onPageChangeListener, ::OnPageChangeDelegate, ::addOnPageChangeListener)