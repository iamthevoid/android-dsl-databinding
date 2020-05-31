package iam.thevoid.noxml.adapterview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class StandaloneFragmentPagerAdapter<T : Any>(
    fm: FragmentManager,
    items: List<T>,
    titles: List<String>
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var bindings: ItemBindings? = null

    var data: MutableList<T> = items.toMutableList()

    private val titles: MutableList<String> = items.indices
        .map { if (it < titles.size) titles[it] else "" }
        .toMutableList()

    fun setUntitledItems(items: List<T>) =
        setTitledItems(StandalonePagerAdapter.fromUntitledItems(items))

    fun setTitledItems(items: List<Pair<String, T>>) {
        data.clear()
        data.addAll(items.map { it.second })

        titles.clear()
        titles.addAll(items.map { it.first })

        notifyDataSetChanged()
    }

    override fun getItem(position: Int) = StandalonePagerFragment().apply {
        factory = {
            bindings?.factory(data[position]::class)?.createLayout(it)?.let { layout ->
                (layout as? Layout<T>)?.run {
                    view.also {
                        set(data[position])
                    }
                }
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    override fun getCount(): Int = data.count()

    class StandalonePagerFragment : Fragment() {

        var factory: ((ViewGroup) -> View?)? = null

        var contentView: View? = null

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? = prepareView(contentView ?: container?.let { factory?.invoke(it) }).also {
            contentView = it
        }

        private fun prepareView(view: View?): View? =
            (view?.parent as? ViewGroup)?.removeView(view).let { view }
    }
}