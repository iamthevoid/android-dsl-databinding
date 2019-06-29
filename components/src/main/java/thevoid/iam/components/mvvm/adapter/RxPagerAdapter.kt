package thevoid.iam.components.mvvm.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class RxPagerAdapter<T : Any>(items: List<T>, titles: List<String> = emptyList()) : PagerAdapter() {

    var bindings = ItemBindings.EMPTY

    private val data: MutableList<T> = items.toMutableList()

    private val titles: MutableList<String> = items.indices
        .map { if (it < titles.size) titles[it] else "" }
        .toMutableList()

    fun setUntitledItems(items: List<T>) =
        setTitledItems(fromUntitledItems(items))

    fun setTitledItems(items: List<Pair<String, T>>) {
        data.clear()
        data.addAll(items.map { it.second })

        titles.clear()
        titles.addAll(items.map { it.first })

        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View =
        (bindings.factory(data[position]::class.java).invoke(container) as? Layout<T>)?.let { layout ->
            ViewHolder(data[position], layout).also {
                container.addView(layout.view)
                it.onBind(data[position])
            }
            layout.view
        } ?: View(container.context)

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) = collection.removeView(view as? View)

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun getCount(): Int = data.count()

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    companion object {
        fun <T> fromUntitledItems(items: List<T>) =
            items.map { Pair("", it) }

        fun <T> fromTitlesAndItems(items: List<T>, titles: List<String>) =
            items.mapIndexed { index, item -> Pair(if (index < titles.size) titles[index] else "", item) }
    }
}