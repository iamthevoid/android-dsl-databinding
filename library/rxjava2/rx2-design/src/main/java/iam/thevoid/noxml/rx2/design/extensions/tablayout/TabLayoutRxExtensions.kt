@file:Suppress("unused")

package iam.thevoid.noxml.rx2.design.extensions.tablayout

import com.google.android.material.tabs.TabLayout
import iam.thevoid.noxml.design.adapter.OnTabSelectedListenerAdapter
import iam.thevoid.noxml.design.local.extensions.tablayout.onTabSelectListener
import iam.thevoid.noxml.rx2.data.onlyPresent
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import iam.thevoid.util.Optional
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor

fun TabLayout.selectTab(tab: Flowable<Int>) =
    selectTab(tab) { it }

fun <T : Any> TabLayout.selectTab(tab: Flowable<T>, mapper: (T) -> Int) =
    addSetter(tab) {
        val tabSelectedListener = onTabSelectListener
        removeOnTabSelectedListener(tabSelectedListener)
        selectTab(getTabAt(mapper(it)))
        addOnTabSelectedListener(tabSelectedListener)
    }

/**
 * onTabReselect
 */

fun TabLayout.onTabReselect(
    onTabReselect: FlowableProcessor<TabLayout.Tab>,
    unwrap: Flowable<Optional<TabLayout.Tab>>.() -> Flowable<TabLayout.Tab> = { onlyPresent() }
) = onTabReselect(onTabReselect, mapper = { it }, unwrap)

fun <T : Any> TabLayout.onTabReselect(
    onTabReselect: FlowableProcessor<T>,
    mapper: ((TabLayout.Tab?) -> T?),
    unwrap: Flowable<Optional<T>>.() -> Flowable<T> = { onlyPresent() }
) = addSetter(Flowable.create<Optional<T>>({
    val callback = object : OnTabSelectedListenerAdapter() {
        override fun onTabReselected(tab: TabLayout.Tab?) = it.onNext(Optional.of(mapper(tab)))
    }
    onTabSelectListener.addOnTabReselectCallback(callback)
    it.setCancellable { onTabSelectListener.removeOnTabReselectCallback(callback) }
}, BackpressureStrategy.LATEST).unwrap().doOnNext(onTabReselect::onNext))

/**
 * onTabUnselected
 */

fun TabLayout.onTabUnselected(
    onTabUnselected: FlowableProcessor<TabLayout.Tab>,
    unwrap: Flowable<Optional<TabLayout.Tab>>.() -> Flowable<TabLayout.Tab> = { onlyPresent() }
) = onTabUnselected(onTabUnselected, mapper = { it }, unwrap)

fun <T : Any> TabLayout.onTabUnselected(
    onTabUnselected: FlowableProcessor<T>,
    mapper: ((TabLayout.Tab?) -> T?),
    unwrap: Flowable<Optional<T>>.() -> Flowable<T> = { onlyPresent() }
) = addSetter(Flowable.create<Optional<T>>({
    val callback = object : OnTabSelectedListenerAdapter() {
        override fun onTabUnselected(tab: TabLayout.Tab?) {
            it.onNext(Optional.of(mapper(tab)))
        }
    }
    onTabSelectListener.addOnTabUnselectedCallback(callback)
    it.setCancellable { onTabSelectListener.removeOnTabUnselectedCallback(callback) }
}, BackpressureStrategy.LATEST).unwrap().doOnNext(onTabUnselected::onNext))

/**
 * onTabSelected
 */

fun TabLayout.onTabSelected(
    onTabSelected: FlowableProcessor<TabLayout.Tab>,
    unwrap: Flowable<Optional<TabLayout.Tab>>.() -> Flowable<TabLayout.Tab> = { onlyPresent() }
) = onTabSelected(onTabSelected, mapper = { it }, unwrap)

fun <T : Any> TabLayout.onTabSelected(
    onTabSelected: FlowableProcessor<T>,
    mapper: ((TabLayout.Tab?) -> T?),
    unwrap: Flowable<Optional<T>>.() -> Flowable<T> = { onlyPresent() }
) = addSetter(Flowable.create<Optional<T>>({
    val callback = object : OnTabSelectedListenerAdapter() {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            it.onNext(Optional.of(mapper(tab)))
        }
    }
    onTabSelectListener.addOnTabSelectedCallback(callback)
    it.setCancellable { onTabSelectListener.removeOnTabSelectedCallback(callback) }
}, BackpressureStrategy.LATEST).unwrap().doOnNext(onTabSelected::onNext))