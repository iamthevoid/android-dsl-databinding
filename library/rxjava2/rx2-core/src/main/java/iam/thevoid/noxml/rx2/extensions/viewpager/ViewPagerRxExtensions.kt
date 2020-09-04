@file:SuppressWarnings("UNUSED")
@file:Suppress("unused")

package iam.thevoid.noxml.rx2.extensions.viewpager

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.change.pager.OnPageScrolled
import iam.thevoid.noxml.core.local.adapters.OnPageChangeListenerAdapter
import iam.thevoid.noxml.core.local.extensions.viewpager.onPageChangeListener
import iam.thevoid.noxml.extensions.viewpager.setItems
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.rxkotlin.Flowables


fun ViewPager.setCurrentPage(page: Flowable<Int>, smoothScroll: Boolean = true) =
    addSetter(page) {
        val onPageChangeListener = onPageChangeListener
        removeOnPageChangeListener(onPageChangeListener)
        setCurrentItem(it, smoothScroll)
        addOnPageChangeListener(onPageChangeListener)
    }

fun <T : Any> ViewPager.setItems(
    items: Flowable<List<T>>,
    itemBindings: ItemBindings
) = addSetter(items) { setItems(it, itemBindings) }

fun <T : Any> ViewPager.setItems(
    items: Flowable<List<T>>,
    titles: Flowable<List<String>>,
    itemBindings: ItemBindings
) = addSetter(Flowables.combineLatest(items, titles)) {
    setItems(it.first, itemBindings, it.second)
}

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: Flowable<List<T>>,
    titles: List<String>,
    itemBindings: ItemBindings
) = addSetter(Flowables.combineLatest(items, Flowable.just(titles))) {
    setItems(fm, it.first, itemBindings, it.second)
}

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: Flowable<List<T>>,
    itemBindings: ItemBindings
) = addSetter(items) { setItems(fm, it, itemBindings) }

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: Flowable<List<T>>,
    titles: Flowable<List<String>>,
    itemBindings: ItemBindings
) = addSetter(Flowables.combineLatest(items, titles)) {
    setItems(fm, it.first, itemBindings, it.second)
}

/**
 * Getter
 */

/**
 * onPageSelect
 */

fun <T : Any> ViewPager.onPageSelect(onPageSelect: FlowableProcessor<T>, mapper: ((Int) -> T)) {
    addSetter(Flowable.create<T>({
        val callback = object : OnPageChangeListenerAdapter() {
            override fun onPageSelected(position: Int) {
                it.onNext(mapper(position))
            }
        }
        onPageChangeListener.addOnPageSelectedCallback(callback)
        it.setCancellable { onPageChangeListener.removeOnPageSelectedCallback(callback) }
    }, BackpressureStrategy.LATEST).doOnNext(onPageSelect::onNext))
}

fun ViewPager.onPageSelect(onPageSelect: FlowableProcessor<Int>) =
    onPageSelect(onPageSelect) { it }

/**
 * onPageScrollStateChanged
 */


fun <T : Any> ViewPager.onPageScrollStateChanged(onPageScrollStateChanged: FlowableProcessor<T>, mapper: ((Int) -> T)) {
    addSetter(Flowable.create<T>({
        val callback = object : OnPageChangeListenerAdapter() {
            override fun onPageScrollStateChanged(state: Int) {
                it.onNext(mapper(state))
            }
        }
        onPageChangeListener.addOnPageScrollStateChanged(callback)
        it.setCancellable { onPageChangeListener.removeOnPageScrollStateChanged(callback) }
    }, BackpressureStrategy.LATEST).doOnNext(onPageScrollStateChanged::onNext))
}

fun ViewPager.onPageScrollStateChanged(onPageScrollStateChanged: FlowableProcessor<Int>) =
    onPageScrollStateChanged(onPageScrollStateChanged) { it }

/**
 * onPageScrolled
 */


fun <T : Any> ViewPager.onPageScrolled(onPageScrolled: FlowableProcessor<T>, mapper: ((OnPageScrolled) -> T)) {
    addSetter(Flowable.create<T>({
        val callback = object : OnPageChangeListenerAdapter() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                it.onNext(mapper(OnPageScrolled(position, positionOffset, positionOffsetPixels)))
            }
        }
        onPageChangeListener.addOnPageScrolledCallback(callback)
        it.setCancellable { onPageChangeListener.removeOnPageScrollStateChanged(callback) }
    }, BackpressureStrategy.LATEST).doOnNext(onPageScrolled::onNext))
}

fun ViewPager.onPageScrolled(onPageScrolled: FlowableProcessor<OnPageScrolled>) =
    onPageScrolled(onPageScrolled) { it }