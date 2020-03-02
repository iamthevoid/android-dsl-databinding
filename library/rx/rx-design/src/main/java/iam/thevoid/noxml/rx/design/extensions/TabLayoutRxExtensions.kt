package iam.thevoid.noxml.rx.design.extensions

import com.google.android.material.tabs.TabLayout
import iam.thevoid.noxml.design.adapter.OnTabSelectedListenerAdapter
import iam.thevoid.noxml.design.extensions.onTabSelectListener
import io.reactivex.Flowable
import iam.thevoid.noxml.rx.data.fields.RxField
import iam.thevoid.noxml.rx.data.fields.RxItem
import iam.thevoid.noxml.rx.extensions.addSetter

fun TabLayout.selectTab(tab: TabLayout.Tab?) {
    post { tab?.select() }
}

fun TabLayout.selectTab(tab: Flowable<Int>) =
    selectTab(tab) { it }

fun <T : Any> TabLayout.selectTab(tab: Flowable<T>, mapper: (T) -> Int) =
    addSetter(tab) {
        val tabSelectedListener = onTabSelectListener
        removeOnTabSelectedListener(tabSelectedListener)
        selectTab(getTabAt(mapper(it)))
        addOnTabSelectedListener(tabSelectedListener)
    }


fun TabLayout.onTabReselect(onTabReselect: RxField<TabLayout.Tab>) =
    onTabReselect(onTabReselect) { it }

fun <T : Any> TabLayout.onTabReselect(onTabReselect: RxField<T>, mapper : ((TabLayout.Tab?) -> T?)) {
    onTabSelectListener.addOnTabReselectCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            onTabReselect.set(mapper(tab))
        }
    })
}

fun <T : Any> TabLayout.onTabReselect(onTabReselect: RxItem<T>, mapper : ((TabLayout.Tab?) -> T)) {
    onTabSelectListener.addOnTabReselectCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            tab?.let(mapper)?.also(onTabReselect::set)
        }
    })
}


fun TabLayout.onTabUnselected(onTabUnselected: RxField<TabLayout.Tab>) =
    onTabUnselected(onTabUnselected) { it }

fun <T : Any> TabLayout.onTabUnselected(onTabUnselected: RxField<T>, mapper : ((TabLayout.Tab?) -> T?)) {
    onTabSelectListener.addOnTabUnselectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabUnselected(tab: TabLayout.Tab?) {
            onTabUnselected.set(mapper(tab))
        }
    })
}

fun <T : Any> TabLayout.onTabUnselected(onTabUnselected: RxItem<T>, mapper : ((TabLayout.Tab?) -> T)) {
    onTabSelectListener.addOnTabUnselectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabUnselected(tab: TabLayout.Tab?) {
            tab?.let(mapper)?.also(onTabUnselected::set)
        }
    })
}


fun TabLayout.onTabSelected(onTabSelected: RxField<TabLayout.Tab>) =
    onTabSelected(onTabSelected) { it }

fun <T : Any> TabLayout.onTabSelected(onTabSelected: RxField<T>, mapper : ((TabLayout.Tab?) -> T?)) {
    onTabSelectListener.addOnTabSelectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            onTabSelected.set(mapper(tab))
        }
    })
}

fun <T : Any> TabLayout.onTabSelected(onTabSelected: RxItem<T>, mapper : ((TabLayout.Tab?) -> T)) {
    onTabSelectListener.addOnTabSelectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let(mapper)?.also(onTabSelected::set)
        }
    })
}