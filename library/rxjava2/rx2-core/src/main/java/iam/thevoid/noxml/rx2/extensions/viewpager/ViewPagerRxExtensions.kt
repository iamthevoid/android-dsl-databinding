@file:SuppressWarnings("UNUSED")
@file:Suppress("unused")

package iam.thevoid.noxml.rx2.extensions.viewpager

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import iam.thevoid.noxml.core.local.adapters.OnPageChangeListenerAdapter
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.change.pager.OnPageScrolled
import iam.thevoid.noxml.core.local.extensions.viewpager.onPageChangeListener
import iam.thevoid.noxml.rx2.data.fields.RxField
import iam.thevoid.noxml.rx2.data.fields.RxInt
import iam.thevoid.noxml.rx2.data.fields.RxItem
import iam.thevoid.noxml.rx2.data.fields.RxList
import iam.thevoid.noxml.extensions.viewpager.setItems
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.rxkotlin.Flowables

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
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
@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun <T : Any> ViewPager.setItems(
    items: RxList<T>,
    itemBindings: ItemBindings
) = addSetter(items.observe()) { setItems(it, itemBindings) }

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
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
@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: RxList<T>,
    itemBindings: ItemBindings
) = addSetter(items.observe()) { setItems(fm, it, itemBindings) }

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
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

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun <T : Any> ViewPager.onPageSelect(onPageSelect: RxField<T>, mapper: ((Int) -> T?)) {
    onPageChangeListener.addOnPageSelectedCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageSelected(position: Int) {
            onPageSelect.set(mapper(position))
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun <T : Any> ViewPager.onPageSelect(onPageSelect: RxItem<T>, mapper: ((Int) -> T)) {
    onPageChangeListener.addOnPageSelectedCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageSelected(position: Int) {
            onPageSelect.set(mapper(position))
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun ViewPager.onPageSelect(onPageSelect: RxInt) =
    onPageSelect(onPageSelect) { it }

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



@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun <T : Any> ViewPager.onPageScrollStateChanged(onPageScrollStateChanged: RxField<T>, mapper: ((Int) -> T)) {
    onPageChangeListener.addOnPageScrollStateChanged(object : OnPageChangeListenerAdapter() {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged.set(mapper(state))
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun <T : Any> ViewPager.onPageScrollStateChanged(onPageScrollStateChanged: RxItem<T>, mapper: ((Int) -> T)) {
    onPageChangeListener.addOnPageScrollStateChanged(object : OnPageChangeListenerAdapter() {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged.set(mapper(state))
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun ViewPager.onPageScrollStateChanged(onPageScrollStateChanged: RxInt) =
    onPageScrollStateChanged(onPageScrollStateChanged) { it }

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




@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun <T : Any> ViewPager.onPageScrolled(onPageScrolled: RxField<T>, mapper: ((OnPageScrolled) -> T)) {
    onPageChangeListener.addOnPageScrolledCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            onPageScrolled.set(mapper(OnPageScrolled(position, positionOffset, positionOffsetPixels)))
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun ViewPager.onPageScrolled(onPageScrolled: RxField<OnPageScrolled>) =
    onPageScrolled(onPageScrolled) { it }

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
