package iam.thevoid.noxml.design.adapter

import com.google.android.material.tabs.TabLayout

open class OnTabSelectedListenerAdapter : TabLayout.OnTabSelectedListener {
    override fun onTabReselected(tab: TabLayout.Tab?) = Unit
    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
    override fun onTabSelected(tab: TabLayout.Tab?) = Unit
}