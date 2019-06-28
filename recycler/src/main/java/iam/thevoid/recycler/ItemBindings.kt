package iam.thevoid.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thevoid.iam.components.mvvm.adapter.Layout

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