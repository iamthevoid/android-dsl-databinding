package iam.thevoid.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class RxAdapter<T : Any>(data: List<T> = emptyList()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = data.toMutableList()
        set(items) {
            diffCallbackFactory?.invoke(field, items)?.let { callback ->
                DiffUtil.calculateDiff(callback).apply {
                    field.clear()
                    field.addAll(items)
                    dispatchUpdatesTo(this@RxAdapter)
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
        bindings.viewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holder)
            holder.onBind(data[position])
    }

    override fun getItemViewType(position: Int): Int = data[position].javaClass.name.hashCode()

    override fun getItemCount(): Int = data.size

    class ItemBindings {

        companion object {
            val EMPTY = ItemBindings()
        }

        private val factories = mutableMapOf<Class<*>, (ViewGroup) -> Layout<*>>()

        fun <T> addBinding(binding: Pair<Class<T>, (ViewGroup) -> Layout<T>>): ItemBindings {
            factories[binding.first] = binding.second
            return this
        }

        fun <T> addBinding(cls: Class<T>, layout: (ViewGroup) -> Layout<T>): ItemBindings =
            addBinding(cls to layout)

        fun viewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            Holder(factory(viewType).invoke(parent))

        @Suppress("UNCHECKED_CAST")
        private fun bindingClass(viewType: Int): Class<*> =
            (factories.keys.find { it.name.hashCode() == viewType }
                ?: throw IllegalArgumentException("View type not found"))

        @Suppress("UNCHECKED_CAST")
        private fun factory(viewType: Int): (ViewGroup) -> Layout<*> = factory(bindingClass(viewType))

        private fun factory(cls: Class<*>): (ViewGroup) -> Layout<*> =
            factories[cls] ?: throw IllegalArgumentException("Type of provided factory is incorrect")
    }
}