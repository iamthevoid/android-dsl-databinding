package thevoid.iam.components.mvvm.adapter

import android.view.ViewGroup

class ItemBindings {

    companion object {
        val EMPTY = ItemBindings()

        fun <T, L : Layout<T>> of(cls: Class<T>, factory : (ViewGroup) -> L) =
            of(cls to factory)

        fun <T, L : Layout<T>> of(binding: Pair<Class<T>, (ViewGroup) -> L>) =
            ItemBindings().addBinding(binding)
    }

    private val factories = mutableMapOf<Class<*>, (ViewGroup) -> Layout<*>>()

    fun <T> addBinding(binding: Pair<Class<T>, (ViewGroup) -> Layout<T>>): ItemBindings {
        factories[binding.first] = binding.second
        return this
    }

    fun <T> addBinding(cls: Class<T>, layout: (ViewGroup) -> Layout<T>): ItemBindings =
        addBinding(cls to layout)

    @Suppress("UNCHECKED_CAST")
    private fun bindingClass(viewType: Int): Class<*> =
        (factories.keys.find { it.name.hashCode() == viewType }
            ?: throw IllegalArgumentException("View type not found"))

    @Suppress("UNCHECKED_CAST")
    fun factory(viewType: Int): (ViewGroup) -> Layout<*> = factory(bindingClass(viewType))

    fun factory(cls: Class<*>): (ViewGroup) -> Layout<*> =
        factories[cls] ?: throw IllegalArgumentException("Type of provided factory is incorrect")
}