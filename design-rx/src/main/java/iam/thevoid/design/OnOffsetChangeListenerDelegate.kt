package iam.thevoid.design

import com.google.android.material.appbar.AppBarLayout

class OnOffsetChangeListenerDelegate : AppBarLayout.OnOffsetChangedListener {

    private val listeners = mutableListOf<AppBarLayout.OnOffsetChangedListener>()

    fun addOnOffsetChangeListener(listener: AppBarLayout.OnOffsetChangedListener) {
        listeners.add(listener)
    }

    fun removeOnOffsetChangeListener(listener: AppBarLayout.OnOffsetChangedListener) {
        listeners.remove(listener)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) =
        listeners.forEach { it.onOffsetChanged(appBarLayout, verticalOffset) }

}