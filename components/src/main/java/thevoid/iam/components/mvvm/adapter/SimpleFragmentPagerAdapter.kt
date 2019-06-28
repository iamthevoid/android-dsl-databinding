package thevoid.iam.components.mvvm.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SimpleFragmentPagerAdapter(
    fm: FragmentManager,
    factories: List<Pair<String, () -> Fragment>> = emptyList(),
    behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) : FragmentPagerAdapter(fm, behavior) {

    var factories: MutableList<() -> Fragment> = factories.map { it.second }.toMutableList()

    var titles: MutableList<String> = factories.map { it.first }.toMutableList()

    fun setUntitledFragments(fragments: List<Fragment>) =
        setTitledFactories(fromUntitledFragments(fragments))

    fun setTitledFragments(titledFragments: List<Pair<String, Fragment>>) =
        setTitledFactories(fromTitledFragments(titledFragments))

    fun setUntitledFactories(factories: List<() -> Fragment>) =
        setTitledFactories(fromUntitledFactories(factories))

    fun setTitledFactories(titledFactories: List<Pair<String, () -> Fragment>>) {
        factories.clear()
        factories.addAll(titledFactories.map { it.second })

        titles.clear()
        titles.addAll(titledFactories.map { it.first })
    }


    override fun getItem(position: Int): Fragment = factories[position].invoke()

    override fun getCount(): Int = factories.count()

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    internal companion object {

        fun fromUntitledFragments(fragments: List<Fragment>) =
            fragments.map { Pair("", { it }) }

        fun fromTitledFragments(fragments: List<Pair<String, Fragment>>) =
            fragments.map { Pair(it.first, { it.second }) }

        fun fromUntitledFactories(fragments: List<() -> Fragment>) =
            fragments.map { Pair("", it) }

    }
}