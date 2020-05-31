package iam.thevoid.noxml.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.adapterview.Layout
import iam.thevoid.noxml.adapterview.factory.LayoutFactory

open class StandaloneRecyclerAdapter<T : Any>(data: List<T> = emptyList()) :
    RecyclerView.Adapter<Holder<T>>() {

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

    private val layoutCache by lazy { mutableMapOf<Int, LayoutFactory<*>>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<T> =
        createLayout(viewType, parent)?.let(::Holder)
            ?: throw IllegalStateException("Binding not provided")

    @Suppress("UNCHECKED_CAST")
    private fun createLayout(viewType: Int, parent: ViewGroup) =
        getLayoutFactory(viewType).createLayout(parent) as? Layout<T>

    private fun getLayoutFactory(viewType: Int) =
        layoutCache[viewType] ?: (bindings.factory(viewType).also { layoutCache[viewType] = it })

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: Holder<T>, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemViewType(position: Int): Int =
        data.getOrNull(position)?.javaClass?.name.hashCode()

    override fun getItemCount(): Int = data.size

}