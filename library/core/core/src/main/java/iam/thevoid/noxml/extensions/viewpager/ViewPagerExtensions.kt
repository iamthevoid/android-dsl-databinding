@file:Suppress("unused")

package iam.thevoid.noxml.extensions.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import iam.thevoid.ae.asAppCompatActivity
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.adapterview.StandalonePagerAdapter
import iam.thevoid.noxml.adapterview.SimpleFragmentPagerAdapter
import iam.thevoid.noxml.adapterview.StandaloneFragmentPagerAdapter

@Suppress("UNCHECKED_CAST")
fun <T : Any> ViewPager.setItems(
    items: List<T>,
    itemBindings: ItemBindings,
    titles: List<String> = emptyList(),
    position: Int = PagerAdapter.POSITION_UNCHANGED
) {
    (adapter as? StandalonePagerAdapter<T>)?.apply {
        setTitledItems(StandalonePagerAdapter.fromTitlesAndItems(items, titles))
    } ?: run {
        StandalonePagerAdapter(items, titles).apply {
            this@apply.position = position
            this@apply.bindings = itemBindings
            adapter = this
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> ViewPager.setItems(
    fm: FragmentManager,
    items: List<T>,
    itemBindings: ItemBindings,
    titles: List<String> = emptyList()
) {
    (adapter as? StandaloneFragmentPagerAdapter<T>)?.apply {
        setTitledItems(StandalonePagerAdapter.fromTitlesAndItems(items, titles))
    } ?: run {
        StandaloneFragmentPagerAdapter(fm, items, titles).apply {
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
        ?: SimpleFragmentPagerAdapter(
            fm,
            fragments,
            behavior
        )
}