package iam.thevoid.noxml.rx2.design.extensions.tablayout

import com.google.android.material.tabs.TabLayout
import iam.thevoid.noxml.design.adapter.OnTabSelectedListenerAdapter
import iam.thevoid.noxml.design.local.extensions.tablayout.onTabSelectListener
import iam.thevoid.noxml.rx2.data.fields.RxField
import iam.thevoid.noxml.rx2.data.fields.RxItem

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun TabLayout.onTabReselect(onTabReselect: RxField<TabLayout.Tab>) =
    onTabReselect(onTabReselect) { it }

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun <T : Any> TabLayout.onTabReselect(onTabReselect: RxField<T>, mapper: ((TabLayout.Tab?) -> T?)) {
    onTabSelectListener.addOnTabReselectCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            onTabReselect.set(mapper(tab))
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun <T : Any> TabLayout.onTabReselect(onTabReselect: RxItem<T>, mapper: ((TabLayout.Tab?) -> T)) {
    onTabSelectListener.addOnTabReselectCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            tab?.let(mapper)?.also(onTabReselect::set)
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun TabLayout.onTabUnselected(onTabUnselected: RxField<TabLayout.Tab>) =
    onTabUnselected(onTabUnselected) { it }

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun <T : Any> TabLayout.onTabUnselected(
    onTabUnselected: RxField<T>,
    mapper: ((TabLayout.Tab?) -> T?)
) {
    onTabSelectListener.addOnTabUnselectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabUnselected(tab: TabLayout.Tab?) {
            onTabUnselected.set(mapper(tab))
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun <T : Any> TabLayout.onTabUnselected(
    onTabUnselected: RxItem<T>,
    mapper: ((TabLayout.Tab?) -> T)
) {
    onTabSelectListener.addOnTabUnselectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabUnselected(tab: TabLayout.Tab?) {
            tab?.let(mapper)?.also(onTabUnselected::set)
        }
    })
}


@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun TabLayout.onTabSelected(onTabSelected: RxField<TabLayout.Tab>) =
    onTabSelected(onTabSelected) { it }

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun <T : Any> TabLayout.onTabSelected(onTabSelected: RxField<T>, mapper: ((TabLayout.Tab?) -> T?)) {
    onTabSelectListener.addOnTabSelectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            onTabSelected.set(mapper(tab))
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun <T : Any> TabLayout.onTabSelected(onTabSelected: RxItem<T>, mapper: ((TabLayout.Tab?) -> T)) {
    onTabSelectListener.addOnTabSelectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let(mapper)?.also(onTabSelected::set)
        }
    })
}