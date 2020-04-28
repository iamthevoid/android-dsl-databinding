package iam.thevoid.noxml.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.adapterview.Layout

open class StandaloneRecyclerAdapter<T : Any>(data: List<T> = emptyList()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = data.toList()
        set(items) {
            diffCallbackFactory(field, items).let { callback ->
                DiffUtil.calculateDiff(callback).apply {
                    field = items
                    dispatchUpdatesTo(this@StandaloneRecyclerAdapter)
                }
            }
        }

    var bindings = ItemBindings.EMPTY

    var diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) =
        diffCallback()

    private val layoutCache by lazy { mutableMapOf<Int, (ViewGroup) -> Layout<*>>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        createLayout(viewType, parent)?.let(::Holder)
            ?: throw IllegalStateException("Binding not provided")

    @Suppress("UNCHECKED_CAST")
    private fun createLayout(viewType: Int, parent: ViewGroup) =
        getLayoutFactory(viewType).invoke(parent) as? Layout<T>

    private fun getLayoutFactory(viewType: Int) =
        layoutCache[viewType] ?: (bindings.factory(viewType).also { layoutCache[viewType] = it })

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? Holder<T>)?.onBind(data[position])
    }

    override fun getItemViewType(position: Int): Int = data[position].javaClass.name.hashCode()

    override fun getItemCount(): Int = data.size

}