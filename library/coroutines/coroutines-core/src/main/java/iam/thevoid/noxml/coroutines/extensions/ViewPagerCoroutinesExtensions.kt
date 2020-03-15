@file:SuppressWarnings("UNUSED")
@file:Suppress("unused")

package iam.thevoid.noxml.coroutines.extensions

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import iam.thevoid.noxml.adapters.OnPageChangeListenerAdapter
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.change.pager.OnPageScrolled
import iam.thevoid.noxml.coroutines.data.CoroutineField
import iam.thevoid.noxml.coroutines.data.CoroutineInt
import iam.thevoid.noxml.coroutines.data.CoroutineItem
import iam.thevoid.noxml.coroutines.data.CoroutineList
import iam.thevoid.noxml.rx2.recycler.extensions.onPageChangeListener
import iam.thevoid.noxml.rx2.recycler.extensions.setItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

fun ViewPager.setCurrentPage(page: CoroutineInt, smoothScroll: Boolean = true) =
    setCurrentPage(page.observe(), smoothScroll)

fun ViewPager.setCurrentPage(page: Flow<Int>, smoothScroll: Boolean = true) =
    addSetter(page) {
        val onPageChangeListener = onPageChangeListener
        removeOnPageChangeListener(onPageChangeListener)
        setCurrentItem(it, smoothScroll)
        addOnPageChangeListener(onPageChangeListener)
    }

// View Pager Adapter

fun <T : Any> ViewPager.setItems(
    items: CoroutineList<T>,
    itemBindings: ItemBindings
) = setItems(items.observe(), itemBindings)

fun <T : Any> ViewPager.setItems(
    items: CoroutineList<T>,
    titles: CoroutineList<String>,
    itemBindings: ItemBindings
) = setItems(items.observe(), titles.observe(), itemBindings)

fun <T : Any> ViewPager.setItems(
    items: Flow<List<T>>,
    itemBindings: ItemBindings
) = addSetter(items) { setItems(it, itemBindings) }

fun <T : Any> ViewPager.setItems(
    items: Flow<List<T>>,
    titles: Flow<List<String>>,
    itemBindings: ItemBindings
) = addSetter(combine(items, titles) { i, t -> Pair(i, t) }) {
    setItems(it.first, itemBindings, it.second)
}

// Fragment pager adapter

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: CoroutineList<T>,
    itemBindings: ItemBindings
) = setItems(fm, items.observe(), itemBindings)

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: CoroutineList<T>,
    titles: CoroutineList<String>,
    itemBindings: ItemBindings
) = setItems(fm, items.observe(), titles.observe(), itemBindings)

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: Flow<List<T>>,
    titles: List<String>,
    itemBindings: ItemBindings
) = setItems(fm, items, flowOf(titles), itemBindings)

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: Flow<List<T>>,
    itemBindings: ItemBindings
) = addSetter(items) { setItems(fm, it, itemBindings) }

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: Flow<List<T>>,
    titles: Flow<List<String>>,
    itemBindings: ItemBindings
) = addSetter(combine(items, titles) { i, t -> Pair(i, t) }) {
    setItems(fm, it.first, itemBindings, it.second)
}

/**
 * Getter
 */

fun <T : Any> ViewPager.onPageSelect(onPageSelect: CoroutineField<T>, mapper: (Int) -> T) {
    onPageChangeListener.addOnPageSelectedCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageSelected(position: Int) {
            onPageSelect.set(mapper(position))
        }
    })
}

fun <T : Any> ViewPager.onPageSelect(onPageSelect: CoroutineItem<T>, mapper: ((Int) -> T)) {
    onPageChangeListener.addOnPageSelectedCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageSelected(position: Int) {
            onPageSelect.set(mapper(position))
        }
    })
}

fun ViewPager.onPageSelect(onPageSelect: CoroutineInt) =
    onPageSelect(onPageSelect) { it }


fun <T : Any> ViewPager.onPageScrollStateChanged(onPageScrollStateChanged: CoroutineField<T>, mapper: ((Int) -> T)) {
    onPageChangeListener.addOnPageScrollStateChanged(object : OnPageChangeListenerAdapter() {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged.set(mapper(state))
        }
    })
}

fun <T : Any> ViewPager.onPageScrollStateChanged(onPageScrollStateChanged: CoroutineItem<T>, mapper: ((Int) -> T)) {
    onPageChangeListener.addOnPageScrollStateChanged(object : OnPageChangeListenerAdapter() {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged.set(mapper(state))
        }
    })
}

fun ViewPager.onPageScrollStateChanged(onPageScrollStateChanged: CoroutineInt) =
    onPageScrollStateChanged(onPageScrollStateChanged) { it }


fun <T : Any> ViewPager.onPageScrolled(onPageScrolled: CoroutineField<T>, mapper: ((OnPageScrolled) -> T)) {
    onPageChangeListener.addOnPageScrolledCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            onPageScrolled.set(mapper(OnPageScrolled(position, positionOffset, positionOffsetPixels)))
        }
    })
}

fun <T : Any> ViewPager.onPageScrolled(onPageScrolled: CoroutineItem<T>, mapper: ((OnPageScrolled) -> T)) {
    onPageChangeListener.addOnPageScrolledCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            onPageScrolled.set(mapper(OnPageScrolled(position, positionOffset, positionOffsetPixels)))
        }
    })
}

fun ViewPager.onPageScrolled(onPageScrolled: CoroutineField<OnPageScrolled>) =
    onPageScrolled(onPageScrolled) { it }


