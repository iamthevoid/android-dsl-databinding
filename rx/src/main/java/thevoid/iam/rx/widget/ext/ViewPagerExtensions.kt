package thevoid.iam.rx.widget.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import iam.thevoid.ae.asAppCompatActivity
import iam.thevoid.rxe.combine
import io.reactivex.Flowable
import thevoid.iam.rx.adapter.ItemBindings
import thevoid.iam.rx.adapter.RxPagerAdapter
import thevoid.iam.rx.adapter.SimpleFragmentPagerAdapter
import thevoid.iam.rx.rxdata.fields.RxInt
import thevoid.iam.rx.rxdata.fields.RxList

fun ViewPager.setCurrentPage(page: Flowable<Int>, smoothScroll: Boolean = true) =
    addSetter(page) { setCurrentItem(it, smoothScroll) }

fun ViewPager.setCurrentPage(page: RxInt, smoothScroll: Boolean = true) =
    addSetter(page.observe()) { setCurrentItem(it, smoothScroll) }

// View Pager Adapter

fun <T : Any> ViewPager.setItems(
    items: RxList<T>,
    itemBindings: ItemBindings
) = addSetter(items.observe()) { setItems(it, itemBindings) }

fun <T : Any> ViewPager.setItems(
    items: RxList<T>,
    titles: RxList<String>,
    itemBindings: ItemBindings
) = addSetter(combine(items.observe(), titles.observe())) { setItems(it.first, itemBindings, it.second) }

fun <T : Any> ViewPager.setItems(
    itemsFlowable: Flowable<List<T>>,
    itemBindings: ItemBindings
) = addSetter(itemsFlowable) { setItems(it, itemBindings) }

fun <T : Any> ViewPager.setItems(
    itemsFlowable: Flowable<List<T>>,
    titlesFlowable: Flowable<List<String>>,
    itemBindings: ItemBindings
) = addSetter(combine(itemsFlowable, titlesFlowable)) { setItems(it.first, itemBindings, it.second) }

fun <T : Any> ViewPager.setItems(
    items : List<T>,
    itemBindings: ItemBindings,
    titles : List<String> = emptyList(),
    position : Int = PagerAdapter.POSITION_UNCHANGED
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