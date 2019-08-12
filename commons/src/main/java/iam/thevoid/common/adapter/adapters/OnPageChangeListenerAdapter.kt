package iam.thevoid.common.adapter.adapters

import androidx.viewpager.widget.ViewPager

open class OnPageChangeListenerAdapter  : ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) = Unit
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
    override fun onPageSelected(position: Int) = Unit
}