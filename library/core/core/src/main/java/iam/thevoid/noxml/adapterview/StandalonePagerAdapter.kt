package iam.thevoid.noxml.adapterview

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.viewpager.widget.PagerAdapter
import iam.thevoid.noxml.adapterview.factory.LayoutFactory
import kotlin.reflect.KClass

open class StandalonePagerAdapter<T : Any>(items: List<T>, titles: List<String> = emptyList()) : PagerAdapter() {

    var bindings = ItemBindings.EMPTY

    @Position
    var position = POSITION_UNCHANGED

    private val data: MutableList<T> = items.toMutableList()

    private val titles: MutableList<String> = items.indices
        .map { if (it < titles.size) titles[it] else "" }
        .toMutableList()

    fun setUntitledItems(items: List<T>) =
        setTitledItems(
            fromUntitledItems(
                items
            )
        )

    fun setTitledItems(items: List<Pair<String, T>>) {
        data.clear()
        data.addAll(items.map { it.second })

        titles.clear()
        titles.addAll(items.map { it.first })

        notifyDataSetChanged()
    }

    private val layoutCache by lazy { mutableMapOf<KClass<out T>, LayoutFactory<*>>() }

    override fun instantiateItem(container: ViewGroup, position: Int): View =
        data[position].let {item ->
            createLayout(item, container)?.let { layout ->
                ViewHolder(item, layout).also {
                    container.addView(layout.view)
                    it.onBind(item)
                }
                layout.view
            }
        } ?: View(container.context)

    @Suppress("UNCHECKED_CAST")
    private fun createLayout(item : T, container: ViewGroup) =
        getLayoutFactory(item).createLayout(container) as? Layout<T>

    private fun getLayoutFactory(item : T) =
        item::class.let { key -> (layoutCache[key] ?: (bindings.factory(key)).also { layoutCache[key] = it }) }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) = collection.removeView(view as? View)

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun getCount(): Int = data.count()

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    override fun getItemPosition(`object`: Any): Int = position

    companion object {
        fun <T> fromUntitledItems(items: List<T>) =
            items.map { Pair("", it) }

        fun <T> fromTitlesAndItems(items: List<T>, titles: List<String>) =
            items.mapIndexed { index, item -> Pair(if (index < titles.size) titles[index] else "", item) }
    }


    @IntDef(POSITION_NONE, POSITION_UNCHANGED)
    @Retention(AnnotationRetention.SOURCE)
    @Target(AnnotationTarget.PROPERTY)
    annotation class Position
}