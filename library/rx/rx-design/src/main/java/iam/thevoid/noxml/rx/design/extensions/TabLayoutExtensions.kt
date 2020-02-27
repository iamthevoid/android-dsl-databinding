package iam.thevoid.noxml.rx.design.extensions

import com.google.android.material.tabs.TabLayout
import iam.thevoid.noxml.design.adapter.OnTabSelectedListenerAdapter
import iam.thevoid.noxml.design.extensions.onTabSelectListener
import io.reactivex.Flowable
import iam.thevoid.noxml.rx.rxdata.fields.RxField
import iam.thevoid.noxml.rx.rxdata.fields.RxItem
import iam.thevoid.noxml.rx.ext.addSetter

fun TabLayout.selectTab(tab: TabLayout.Tab?) {
    post { tab?.select() }
}

fun TabLayout.selectTab(tabFlowable: Flowable<Int>) =
    selectTab(tabFlowable) { it }

fun <T : Any> TabLayout.selectTab(tabFlowable: Flowable<T>, mapper: (T) -> Int) =
    addSetter(tabFlowable) {
        val tabSelectedListener = onTabSelectListener
        removeOnTabSelectedListener(tabSelectedListener)
        selectTab(getTabAt(mapper(it)))
        addOnTabSelectedListener(tabSelectedListener)
    }


fun TabLayout.onTabReselect(rxTab: RxField<TabLayout.Tab>) =
    onTabReselect(rxTab) { it }

fun <T : Any> TabLayout.onTabReselect(rxField: RxField<T>, mapper : ((TabLayout.Tab?) -> T?)) {
    onTabSelectListener.addOnTabReselectCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            rxField.set(mapper(tab))
        }
    })
}

fun <T : Any> TabLayout.onTabReselect(rxItem: RxItem<T>, mapper : ((TabLayout.Tab?) -> T)) {
    onTabSelectListener.addOnTabReselectCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            tab?.also { nonNullTab -> rxItem.set(mapper(nonNullTab)) }
        }
    })
}


fun TabLayout.onTabUnselected(rxTab: RxField<TabLayout.Tab>) =
    onTabUnselected(rxTab) { it }

fun <T : Any> TabLayout.onTabUnselected(rxField: RxField<T>, mapper : ((TabLayout.Tab?) -> T?)) {
    onTabSelectListener.addOnTabUnselectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabUnselected(tab: TabLayout.Tab?) {
            rxField.set(mapper(tab))
        }
    })
}

fun <T : Any> TabLayout.onTabUnselected(rxItem: RxItem<T>, mapper : ((TabLayout.Tab?) -> T)) {
    onTabSelectListener.addOnTabUnselectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabUnselected(tab: TabLayout.Tab?) {
            tab?.also { nonNullTab -> rxItem.set(mapper(nonNullTab)) }
        }
    })
}


fun TabLayout.onTabSelected(rxTab: RxField<TabLayout.Tab>) =
    onTabSelected(rxTab) { it }

fun <T : Any> TabLayout.onTabSelected(rxField: RxField<T>, mapper : ((TabLayout.Tab?) -> T?)) {
    onTabSelectListener.addOnTabSelectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            rxField.set(mapper(tab))
        }
    })
}

fun <T : Any> TabLayout.onTabSelected(rxItem: RxItem<T>, mapper : ((TabLayout.Tab?) -> T)) {
    onTabSelectListener.addOnTabSelectedCallback(object : OnTabSelectedListenerAdapter() {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.also { nonNullTab -> rxItem.set(mapper(nonNullTab)) }
        }
    })
}