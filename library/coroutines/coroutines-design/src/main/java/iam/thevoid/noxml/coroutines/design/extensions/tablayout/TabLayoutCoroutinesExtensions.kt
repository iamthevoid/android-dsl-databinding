package iam.thevoid.noxml.coroutines.design.extensions.tablayout

import com.google.android.material.tabs.TabLayout
import iam.thevoid.noxml.coroutines.data.CoroutineField
import iam.thevoid.noxml.coroutines.data.CoroutineItem
import iam.thevoid.noxml.coroutines.extensions.view.addSetter
import iam.thevoid.noxml.design.adapter.OnTabSelectedListenerAdapter
import iam.thevoid.noxml.design.local.extensions.tablayout.onTabSelectListener
import kotlinx.coroutines.flow.Flow

fun TabLayout.selectTab(tab: TabLayout.Tab?) {
    post { tab?.select() }
}

fun TabLayout.selectTab(tab: Flow<Int>) =
    selectTab(tab) { it }

fun <T : Any> TabLayout.selectTab(tab: Flow<T>, mapper: (T) -> Int) =
    addSetter(tab) {
        val tabSelectedListener = onTabSelectListener
        removeOnTabSelectedListener(tabSelectedListener)
        selectTab(getTabAt(mapper(it)))
        addOnTabSelectedListener(tabSelectedListener)
    }


fun TabLayout.onTabReselect(onTabReselect: CoroutineField<TabLayout.Tab>) =
    onTabReselect(onTabReselect) { it }

fun <T : Any> TabLayout.onTabReselect(onTabReselect: CoroutineItem<T>, mapper : ((TabLayout.Tab) -> T)) {
    onTabSelectListener.addOnTabReselectCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            tab?.let(mapper)?.also(onTabReselect::set)
        }
    })
}

fun <T : Any> TabLayout.onTabReselect(onTabReselect: CoroutineField<T>, mapper : ((TabLayout.Tab?) -> T?)) {
    onTabSelectListener.addOnTabReselectCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            onTabReselect.set(mapper(tab))
        }
    })
}

fun TabLayout.onTabUnselected(onTabUnselected: CoroutineField<TabLayout.Tab>) =
    onTabUnselected(onTabUnselected) { it }

fun <T : Any> TabLayout.onTabUnselected(onTabUnselected: CoroutineItem<T>, mapper : ((TabLayout.Tab) -> T)) {
    onTabSelectListener.addOnTabUnselectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabUnselected(tab: TabLayout.Tab?) {
            tab?.let(mapper)?.also(onTabUnselected::set)
        }
    })
}

fun <T : Any> TabLayout.onTabUnselected(onTabUnselected: CoroutineField<T>, mapper : ((TabLayout.Tab?) -> T?)) {
    onTabSelectListener.addOnTabUnselectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabUnselected(tab: TabLayout.Tab?) {
            onTabUnselected.set(mapper(tab))
        }
    })
}

fun TabLayout.onTabSelected(onTabSelected: CoroutineField<TabLayout.Tab>) =
    onTabSelected(onTabSelected) { it }

fun <T : Any> TabLayout.onTabSelected(onTabSelected: CoroutineItem<T>, mapper : ((TabLayout.Tab) -> T)) {
    onTabSelectListener.addOnTabSelectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let(mapper)?.also(onTabSelected::set)
        }
    })
}

fun <T : Any> TabLayout.onTabSelected(onTabSelected: CoroutineField<T>, mapper : ((TabLayout.Tab?) -> T?)) {
    onTabSelectListener.addOnTabSelectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            onTabSelected.set(mapper(tab))
        }
    })
}