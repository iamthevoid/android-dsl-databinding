package thevoid.iam.components.widget.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import iam.thevoid.ae.asAppCompatActivity
import io.reactivex.Flowable
import thevoid.iam.components.mvvm.adapter.SimpleFragmentPagerAdapter
import thevoid.iam.components.rx.fields.RxInt

fun ViewPager.setCurrentPage(page: Flowable<Int>, smoothScroll: Boolean = true) =
    addSetter(page) { setCurrentItem(it, smoothScroll) }

fun ViewPager.setCurrentPage(page: RxInt, smoothScroll: Boolean = true) =
    addSetter(page.observe()) { setCurrentItem(it, smoothScroll) }

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