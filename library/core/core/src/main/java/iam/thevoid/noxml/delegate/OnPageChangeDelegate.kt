package iam.thevoid.noxml.delegate

import androidx.viewpager.widget.ViewPager

class OnPageChangeDelegate : ViewPager.OnPageChangeListener {

    private val onPageScrollStateChangedCallback by lazy { mutableListOf<ViewPager.OnPageChangeListener>() }
    private val onPageScrolledCallback by lazy { mutableListOf<ViewPager.OnPageChangeListener>() }
    private val onPageSelectedCallback by lazy { mutableListOf<ViewPager.OnPageChangeListener>() }

    override fun onPageScrollStateChanged(state: Int) =
        onPageScrollStateChangedCallback.forEach { it.onPageScrollStateChanged(state) }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) =
        onPageScrolledCallback.forEach { it.onPageScrolled(position, positionOffset, positionOffsetPixels) }

    override fun onPageSelected(position: Int) =
        onPageSelectedCallback.forEach { it.onPageSelected(position) }

    fun addOnPageScrollStateChanged(callback: ViewPager.OnPageChangeListener) {
        onPageScrollStateChangedCallback.add(callback)
    }

    fun addOnPageScrolledCallback(callback: ViewPager.OnPageChangeListener) {
        onPageScrolledCallback.add(callback)
    }

    fun addOnPageSelectedCallback(callback: ViewPager.OnPageChangeListener) {
        onPageSelectedCallback.add(callback)
    }


    fun removeOnPageScrollStateChanged(callback: ViewPager.OnPageChangeListener) {
        onPageScrollStateChangedCallback.remove(callback)
    }

    fun removePageScrolledCallback(callback: ViewPager.OnPageChangeListener) {
        onPageScrolledCallback.remove(callback)
    }

    fun removeOnPageSelectedCallback(callback: ViewPager.OnPageChangeListener) {
        onPageSelectedCallback.remove(callback)
    }
}
