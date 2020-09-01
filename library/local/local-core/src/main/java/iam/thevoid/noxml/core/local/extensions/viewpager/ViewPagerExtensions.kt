@file:Suppress("unused")

package iam.thevoid.noxml.core.local.extensions.viewpager

import androidx.viewpager.widget.ViewPager
import iam.thevoid.noxml.core.local.R
import iam.thevoid.noxml.core.local.delegate.OnPageChangeDelegate

val ViewPager.onPageChangeListener: OnPageChangeDelegate
    get() = ((getTag(R.id.onPageChangeListener) as? OnPageChangeDelegate)
        ?: OnPageChangeDelegate().also {
            setTag(R.id.onPageChangeListener, it)
            addOnPageChangeListener(it)
        })