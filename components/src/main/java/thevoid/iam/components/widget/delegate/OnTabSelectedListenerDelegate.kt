package thevoid.iam.components.widget.delegate

import com.google.android.material.tabs.TabLayout

class OnTabSelectedListenerDelegate : TabLayout.OnTabSelectedListener {

    private val onTabReselectCallback by lazy { mutableListOf<TabLayout.OnTabSelectedListener>() }
    private val onTabUnselectedCallback by lazy { mutableListOf<TabLayout.OnTabSelectedListener>() }
    private val onTabSelectedCallback by lazy { mutableListOf<TabLayout.OnTabSelectedListener>() }

    override fun onTabReselected(tab: TabLayout.Tab?) =
        onTabReselectCallback.forEach { it.onTabReselected(tab) }

    override fun onTabUnselected(tab: TabLayout.Tab?) =
        onTabUnselectedCallback.forEach { it.onTabUnselected(tab) }

    override fun onTabSelected(tab: TabLayout.Tab?) =
        onTabSelectedCallback.forEach { it.onTabSelected(tab) }


    fun addOnTabReselectCallback(callback: TabLayout.OnTabSelectedListener) {
        onTabReselectCallback.add(callback)
    }

    fun addOnTabUnselectedCallback(callback: TabLayout.OnTabSelectedListener) {
        onTabUnselectedCallback.add(callback)
    }

    fun addOnTabSelectedCallback(callback: TabLayout.OnTabSelectedListener) {
        onTabSelectedCallback.add(callback)
    }


    fun removeOnTabReselectCallback(callback: TabLayout.OnTabSelectedListener) {
        onTabReselectCallback.remove(callback)
    }

    fun removeOnTabUnselectedCallback(callback: TabLayout.OnTabSelectedListener) {
        onTabUnselectedCallback.remove(callback)
    }

    fun removeOnTabSelectedCallback(callback: TabLayout.OnTabSelectedListener) {
        onTabSelectedCallback.remove(callback)
    }
}
