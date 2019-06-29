package iam.thevoid.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import thevoid.iam.components.mvvm.adapter.ItemBindings

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
        Holder(bindings.factory(viewType).invoke(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holder)
            holder.onBind(data[position])
    }

    override fun getItemViewType(position: Int): Int = data[position].javaClass.name.hashCode()

    override fun getItemCount(): Int = data.size
}