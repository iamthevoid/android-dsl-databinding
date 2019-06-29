package thevoid.iam.components.mvvm.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class RxPagerPager<T : Any>(items: List<T>, titles: List<String> = emptyList()) : PagerAdapter() {

    var bindings = ItemBindings.EMPTY

    private val data: MutableList<T>

    private val titles: MutableList<String>

    init {
        this.titles = items.mapIndexed { index, item -> if (titles.size <= index + 1) titles[index] else "" }.toMutableList()
        this.data = items.toMutableList()
    }

    fun setUntitledItems(items: List<T>) =
        setTitledItems(fromUntitledItems(items))

    fun setTitledItems(items: List<Pair<String, T>>) {
        data.clear()
        data.addAll(items.map { it.second })

        titles.clear()
        titles.addAll(items.map { it.first })
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any =
        ViewHolder(bindings.factory(data[position]::class.java).invoke(container))

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) = collection.removeView(view as? View)

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun getCount(): Int = data.count()

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    companion object {
        fun <T> fromUntitledItems(items : List<T>) =
            items.map { Pair("", it) }

        fun <T> fromTitlesAndItems(items : List<T>, titles : List<String>) =
            items.mapIndexed { index, item -> Pair(if (titles.size <= index + 1) titles[index] else "", item) }
    }
}