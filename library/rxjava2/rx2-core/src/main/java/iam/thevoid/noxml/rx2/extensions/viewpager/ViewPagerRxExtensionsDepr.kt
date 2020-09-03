package iam.thevoid.noxml.rx2.extensions.viewpager

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.change.pager.OnPageScrolled
import iam.thevoid.noxml.core.local.adapters.OnPageChangeListenerAdapter
import iam.thevoid.noxml.core.local.extensions.viewpager.onPageChangeListener
import iam.thevoid.noxml.extensions.viewpager.setItems
import iam.thevoid.noxml.rx2.data.fields.RxField
import iam.thevoid.noxml.rx2.data.fields.RxInt
import iam.thevoid.noxml.rx2.data.fields.RxItem
import iam.thevoid.noxml.rx2.data.fields.RxList
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import io.reactivex.rxkotlin.Flowables

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun ViewPager.setCurrentPage(page: RxInt, smoothScroll: Boolean = true) =
    setCurrentPage(page.observe(), smoothScroll)


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