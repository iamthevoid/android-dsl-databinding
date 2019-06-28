package thevoid.iam.components.mvvm.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import thevoid.iam.components.mvvm.view.MvvmFragment

class SimpleFragmentPagerAdapter(
    fm: FragmentManager,
    factories: List<() -> Fragment> = emptyList(),
    behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) : FragmentPagerAdapter(fm, behavior) {

    var data = factories.toMutableList()
        set(items) {
            field.clear()
            field.addAll(items)
            notifyDataSetChanged()
        }

    override fun getItem(position: Int): Fragment = data[position].invoke()

    override fun getCount(): Int = data.count()
}