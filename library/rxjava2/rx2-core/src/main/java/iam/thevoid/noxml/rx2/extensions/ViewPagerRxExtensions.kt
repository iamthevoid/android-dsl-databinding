@file:SuppressWarnings("UNUSED")
@file:Suppress("unused")

package iam.thevoid.noxml.rx2.extensions

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import iam.thevoid.noxml.adapters.OnPageChangeListenerAdapter
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.change.pager.OnPageScrolled
import iam.thevoid.noxml.rx2.data.fields.RxField
import iam.thevoid.noxml.rx2.data.fields.RxInt
import iam.thevoid.noxml.rx2.data.fields.RxItem
import iam.thevoid.noxml.rx2.data.fields.RxList
import iam.thevoid.noxml.rx2.recycler.extensions.onPageChangeListener
import iam.thevoid.noxml.rx2.recycler.extensions.setItems
import io.reactivex.Flowable
import io.reactivex.rxkotlin.Flowables

fun ViewPager.setCurrentPage(page: RxInt, smoothScroll: Boolean = true) =
    setCurrentPage(page.observe(), smoothScroll)

fun ViewPager.setCurrentPage(page: Flowable<Int>, smoothScroll: Boolean = true) =
    addSetter(page) {
        val onPageChangeListener = onPageChangeListener
        removeOnPageChangeListener(onPageChangeListener)
        setCurrentItem(it, smoothScroll)
        addOnPageChangeListener(onPageChangeListener)
    }

// View Pager Adapter

fun <T : Any> ViewPager.setItems(
    items: RxList<T>,
    itemBindings: ItemBindings
) = addSetter(items.observe()) { setItems(it, itemBindings) }

fun <T : Any> ViewPager.setItems(
    items: RxList<T>,
    titles: RxList<String>,
    itemBindings: ItemBindings
) = addSetter(Flowables.combineLatest(items.observe(), titles.observe())) {
    setItems(
        it.first,
        itemBindings,
        it.second
    )
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
    setItems(
        it.first,
        itemBindings,
        it.second
    )
}

// Fragment pager adapter

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: RxList<T>,
    itemBindings: ItemBindings
) = addSetter(items.observe()) { setItems(fm, it, itemBindings) }

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: RxList<T>,
    titles: RxList<String>,
    itemBindings: ItemBindings
) = addSetter(Flowables.combineLatest(items.observe(), titles.observe())) {
    setItems(fm, it.first, itemBindings, it.second)
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

fun <T : Any> ViewPager.onPageSelect(onPageSelect: RxField<T>, mapper: ((Int) -> T?)) {
    onPageChangeListener.addOnPageSelectedCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageSelected(position: Int) {
            onPageSelect.set(mapper(position))
        }
    })
}

fun <T : Any> ViewPager.onPageSelect(onPageSelect: RxItem<T>, mapper: ((Int) -> T)) {
    onPageChangeListener.addOnPageSelectedCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageSelected(position: Int) {
            onPageSelect.set(mapper(position))
        }
    })
}

fun ViewPager.onPageSelect(onPageSelect: RxInt) =
    onPageSelect(onPageSelect) { it }


fun <T : Any> ViewPager.onPageScrollStateChanged(onPageScrollStateChanged: RxField<T>, mapper: ((Int) -> T)) {
    onPageChangeListener.addOnPageScrollStateChanged(object : OnPageChangeListenerAdapter() {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged.set(mapper(state))
        }
    })
}

fun <T : Any> ViewPager.onPageScrollStateChanged(onPageScrollStateChanged: RxItem<T>, mapper: ((Int) -> T)) {
    onPageChangeListener.addOnPageScrollStateChanged(object : OnPageChangeListenerAdapter() {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged.set(mapper(state))
        }
    })
}

fun ViewPager.onPageScrollStateChanged(onPageScrollStateChanged: RxInt) =
    onPageScrollStateChanged(onPageScrollStateChanged) { it }


fun <T : Any> ViewPager.onPageScrolled(onPageScrolled: RxField<T>, mapper: ((OnPageScrolled) -> T)) {
    onPageChangeListener.addOnPageScrolledCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            onPageScrolled.set(mapper(OnPageScrolled(position, positionOffset, positionOffsetPixels)))
        }
    })
}

fun ViewPager.onPageScrolled(onPageScrolled: RxField<OnPageScrolled>) =
    onPageScrolled(onPageScrolled) { it }
