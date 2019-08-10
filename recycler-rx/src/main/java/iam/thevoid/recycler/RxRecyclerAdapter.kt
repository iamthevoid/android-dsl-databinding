package iam.thevoid.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import thevoid.iam.rx.adapter.ItemBindings
import thevoid.iam.rx.adapter.Layout

open class RxRecyclerAdapter<T : Any>(data: List<T> = emptyList()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = data.toMutableList()
        set(items) {
            diffCallbackFactory?.invoke(field, items)?.let { callback ->
                DiffUtil.calculateDiff(callback).apply {
                    field.clear()
                    field.addAll(items)
                    dispatchUpdatesTo(this@RxRecyclerAdapter)
                }
            } ?: run {
                field.clear()
                field.addAll(items)
                notifyDataSetChanged()
            }
        }

    var bindings = ItemBindings.EMPTY

    var diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>)? = null

    private val layoutCache by lazy { mutableMapOf<Int, (ViewGroup) -> Layout<*>>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        createLayout(viewType, parent)?.let { Holder(it) } ?: throw IllegalStateException("Binding not provided")

    private fun createLayout(viewType: Int, parent: ViewGroup) =
        getLayoutFactory(viewType).invoke(parent) as? Layout<T>

    private fun getLayoutFactory(viewType: Int) =
        layoutCache[viewType] ?: (bindings.factory(viewType).also { layoutCache[viewType] = it })

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? Holder<T>)?.onBind(data[position])
    }

    override fun getItemViewType(position: Int): Int = data[position].javaClass.name.hashCode()

    override fun getItemCount(): Int = data.size
}