package thevoid.iam.rx.widget.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import iam.thevoid.ae.asAppCompatActivity
import iam.thevoid.common.adapter.adapters.OnPageChangeListenerAdapter
import iam.thevoid.common.adapter.change.pager.OnPageScrolled
import iam.thevoid.common.adapter.delegate.OnPageChangeDelegate
import iam.thevoid.rxe.combine
import io.reactivex.Flowable
import thevoid.iam.rx.R
import thevoid.iam.rx.adapter.ItemBindings
import thevoid.iam.rx.adapter.RxFragmentPagerAdapter
import thevoid.iam.rx.adapter.RxPagerAdapter
import thevoid.iam.rx.adapter.SimpleFragmentPagerAdapter
import thevoid.iam.rx.rxdata.fields.RxField
import thevoid.iam.rx.rxdata.fields.RxInt
import thevoid.iam.rx.rxdata.fields.RxItem
import thevoid.iam.rx.rxdata.fields.RxList

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
            setTag(R.id.onTabSelectListener, it)
            addOnPageChangeListener(it)
        })


fun ViewPager.onPageSelect(rxPage: RxInt) =
    onPageSelect(rxPage) { it }

fun <T : Any> ViewPager.onPageSelect(rxField: RxField<T>, mapper: ((Int) -> T?)) =
    addGetter({
        onPageChangeListener.addOnPageSelectedCallback(object : OnPageChangeListenerAdapter() {
            override fun onPageSelected(position: Int) {
                it.invoke(mapper(position))
            }
        })
    }, rxField)

fun <T : Any> ViewPager.onPageSelect(rxItem: RxItem<T>, mapper: ((Int) -> T)) =
    addGetter({
        onPageChangeListener.addOnPageSelectedCallback(object : OnPageChangeListenerAdapter() {
            override fun onPageSelected(position: Int) {
                it.invoke(mapper(position))
            }
        })
    }, rxItem)

fun ViewPager.onPageScrollStateChanged(rxInt: RxInt) =
    onPageScrollStateChanged(rxInt) { it }

fun <T : Any> ViewPager.onPageScrollStateChanged(rxField: RxField<T>, mapper: ((Int) -> T)) =
    addGetter({
        onPageChangeListener.addOnPageScrollStateChanged(object : OnPageChangeListenerAdapter() {
            override fun onPageScrollStateChanged(state: Int) {
                it.invoke(mapper(state))
            }
        })
    }, rxField)

fun <T : Any> ViewPager.onPageScrollStateChanged(rxItem: RxItem<T>, mapper: ((Int) -> T)) =
    addGetter({
        onPageChangeListener.addOnPageScrollStateChanged(object : OnPageChangeListenerAdapter() {
            override fun onPageScrollStateChanged(state: Int) {
                it.invoke(mapper(state))
            }
        })
    }, rxItem)

fun ViewPager.onPageScrolled(rxField: RxField<OnPageScrolled>) =
    onPageScrolled(rxField) { it }

fun <T : Any> ViewPager.onPageScrolled(rxField: RxField<T>, mapper: ((OnPageScrolled) -> T)) =
    addGetter({
        onPageChangeListener.addOnPageScrolledCallback(object : OnPageChangeListenerAdapter() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                it.invoke(mapper(OnPageScrolled(position, positionOffset, positionOffsetPixels)))
            }
        })
    }, rxField)

