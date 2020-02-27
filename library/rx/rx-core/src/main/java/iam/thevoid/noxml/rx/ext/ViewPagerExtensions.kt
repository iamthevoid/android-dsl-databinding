@file:SuppressWarnings("UNUSED")
@file:Suppress("unused")

package iam.thevoid.noxml.rx.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import iam.thevoid.ae.asAppCompatActivity
import iam.thevoid.noxml.adapters.OnPageChangeListenerAdapter
import iam.thevoid.noxml.change.pager.OnPageScrolled
import iam.thevoid.noxml.delegate.OnPageChangeDelegate
import iam.thevoid.rxe.combine
import io.reactivex.Flowable
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.rx.R
import iam.thevoid.noxml.rx.adapter.RxFragmentPagerAdapter
import iam.thevoid.noxml.rx.adapter.RxPagerAdapter
import iam.thevoid.noxml.rx.adapter.SimpleFragmentPagerAdapter
import iam.thevoid.noxml.rx.rxdata.fields.RxField
import iam.thevoid.noxml.rx.rxdata.fields.RxInt
import iam.thevoid.noxml.rx.rxdata.fields.RxItem
import iam.thevoid.noxml.rx.rxdata.fields.RxList

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
) = addSetter(combine(items.observe(), titles.observe())) {
    setItems(
        it.first,
        itemBindings,
        it.second
    )
}

fun <T : Any> ViewPager.setItems(
    itemsFlowable: Flowable<List<T>>,
    itemBindings: ItemBindings
) = addSetter(itemsFlowable) { setItems(it, itemBindings) }

fun <T : Any> ViewPager.setItems(
    itemsFlowable: Flowable<List<T>>,
    titlesFlowable: Flowable<List<String>>,
    itemBindings: ItemBindings
) = addSetter(combine(itemsFlowable, titlesFlowable)) {
    setItems(
        it.first,
        itemBindings,
        it.second
    )
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> ViewPager.setItems(
    items: List<T>,
    itemBindings: ItemBindings,
    titles: List<String> = emptyList(),
    position: Int = PagerAdapter.POSITION_UNCHANGED
) {
    (adapter as? RxPagerAdapter<T>)?.apply {
        setTitledItems(RxPagerAdapter.fromTitlesAndItems(items, titles))
    } ?: run {
        RxPagerAdapter(items, titles).apply {
            this@apply.position = position
            this@apply.bindings = itemBindings
            adapter = this
        }
    }
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
) = addSetter(combine(items.observe(), titles.observe())) {
    setItems(fm, it.first, itemBindings, it.second)
}

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: Flowable<List<T>>,
    titles: List<String>,
    itemBindings: ItemBindings
) = addSetter(combine(items, Flowable.just(titles))) {
    setItems(fm, it.first, itemBindings, it.second)
}

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    itemsFlowable: Flowable<List<T>>,
    itemBindings: ItemBindings
) = addSetter(itemsFlowable) { setItems(fm, it, itemBindings) }

fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    itemsFlowable: Flowable<List<T>>,
    titlesFlowable: Flowable<List<String>>,
    itemBindings: ItemBindings
) = addSetter(combine(itemsFlowable, titlesFlowable)) {
    setItems(fm, it.first, itemBindings, it.second)
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: List<T>,
    itemBindings: ItemBindings,
    titles: List<String> = emptyList()
) {
    (adapter as? RxFragmentPagerAdapter<T>)?.apply {
        setTitledItems(RxPagerAdapter.fromTitlesAndItems(items, titles))
    } ?: run {
        RxFragmentPagerAdapter(fm, items, titles).apply {
            this@apply.bindings = itemBindings
            adapter = this
        }
    }
}

// Fragment Pager Adapter

fun ViewPager.setUntitledFragments(
    fragments: List<Fragment>,
    fm: FragmentManager = context.asAppCompatActivity().supportFragmentManager,
    behavior: Int = FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) = setTitledFactories(SimpleFragmentPagerAdapter.fromUntitledFragments(fragments), fm, behavior)

fun ViewPager.setTitledFragments(
    fragments: List<Pair<String, Fragment>>,
    fm: FragmentManager = context.asAppCompatActivity().supportFragmentManager,
    behavior: Int = FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) = setTitledFactories(SimpleFragmentPagerAdapter.fromTitledFragments(fragments), fm, behavior)

fun ViewPager.setUntitledFactories(
    fragments: List<() -> Fragment>,
    fm: FragmentManager = context.asAppCompatActivity().supportFragmentManager,
    behavior: Int = FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) = setTitledFactories(SimpleFragmentPagerAdapter.fromUntitledFactories(fragments), fm, behavior)

fun ViewPager.setTitledFactories(
    fragments: List<Pair<String, () -> Fragment>>,
    fm: FragmentManager = context.asAppCompatActivity().supportFragmentManager,
    behavior: Int = FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    adapter = (adapter as? SimpleFragmentPagerAdapter)?.apply { setTitledFactories(fragments) }
        ?: SimpleFragmentPagerAdapter(fm, fragments, behavior)
}

// On Page Change Listener

private val ViewPager.onPageChangeListener: OnPageChangeDelegate
    get() = ((getTag(R.id.onPageChangeListener) as? OnPageChangeDelegate)
        ?: OnPageChangeDelegate().also {
            setTag(R.id.onPageChangeListener, it)
            addOnPageChangeListener(it)
        })


fun ViewPager.onPageSelect(rxPage: RxInt) =
    onPageSelect(rxPage) { it }

fun <T : Any> ViewPager.onPageSelect(rxField: RxField<T>, mapper: ((Int) -> T?)) {
    onPageChangeListener.addOnPageSelectedCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageSelected(position: Int) {
            rxField.set(mapper(position))
        }
    })
}

fun <T : Any> ViewPager.onPageSelect(rxItem: RxItem<T>, mapper: ((Int) -> T)) {
    onPageChangeListener.addOnPageSelectedCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageSelected(position: Int) {
            rxItem.set(mapper(position))
        }
    })
}

fun ViewPager.onPageScrollStateChanged(rxInt: RxInt) =
    onPageScrollStateChanged(rxInt) { it }

fun <T : Any> ViewPager.onPageScrollStateChanged(rxField: RxField<T>, mapper: ((Int) -> T)) {
    onPageChangeListener.addOnPageScrollStateChanged(object : OnPageChangeListenerAdapter() {
        override fun onPageScrollStateChanged(state: Int) {
            rxField.set(mapper(state))
        }
    })
}

fun <T : Any> ViewPager.onPageScrollStateChanged(rxItem: RxItem<T>, mapper: ((Int) -> T)) {
    onPageChangeListener.addOnPageScrollStateChanged(object : OnPageChangeListenerAdapter() {
        override fun onPageScrollStateChanged(state: Int) {
            rxItem.set(mapper(state))
        }
    })
}

fun ViewPager.onPageScrolled(rxField: RxField<OnPageScrolled>) =
    onPageScrolled(rxField) { it }

fun <T : Any> ViewPager.onPageScrolled(rxField: RxField<T>, mapper: ((OnPageScrolled) -> T)) {
    onPageChangeListener.addOnPageScrolledCallback(object : OnPageChangeListenerAdapter() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            rxField.set(mapper(OnPageScrolled(position, positionOffset, positionOffsetPixels)))
        }
    })
}
