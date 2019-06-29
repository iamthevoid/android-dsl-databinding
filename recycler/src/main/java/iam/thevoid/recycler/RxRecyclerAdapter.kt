package iam.thevoid.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import thevoid.iam.components.mvvm.adapter.ItemBindings
import thevoid.iam.components.mvvm.adapter.Layout

class RxRecyclerAdapter<T : Any>(data: List<T> = emptyList()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        (bindings.factory(viewType).invoke(parent) as? Layout<T>)
            ?.let { layout -> Holder(layout) } ?: throw IllegalStateException("Binding not provided")

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? Holder<T>)?.onBind(data[position])
    }

    override fun getItemViewType(position: Int): Int = data[position].javaClass.name.hashCode()

    override fun getItemCount(): Int = data.size
}