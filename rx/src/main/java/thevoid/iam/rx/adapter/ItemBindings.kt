package thevoid.iam.rx.adapter

import android.view.ViewGroup
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmName

class ItemBindings {

    companion object {

        val EMPTY = ItemBindings()

        fun <T : Any> of(cls: Class<T>, factory: (ViewGroup) -> Layout<T>) =
            of(cls.kotlin, factory)

        fun <T : Any> of(cls: KClass<T>, factory: (ViewGroup) -> Layout<T>) =
            of(cls to factory)

        fun <T : Any> of(binding: Pair<KClass<T>, (ViewGroup) -> Layout<T>>) =
            ItemBindings().addBinding(binding)
    }

    private val factories = mutableMapOf<KClass<*>, (ViewGroup) -> Layout<*>>()

    fun <T : Any> addBinding(binding: Pair<KClass<T>, (ViewGroup) -> Layout<T>>): ItemBindings {
        factories[binding.first] = binding.second
        return this
    }

    fun <T : Any> addBinding(cls: KClass<T>, layout: (ViewGroup) -> Layout<T>): ItemBindings =
        addBinding(cls to layout)

    fun <T : Any> addBinding(cls: Class<T>, layout: (ViewGroup) -> Layout<T>): ItemBindings =
        addBinding(cls.kotlin to layout)

    @Suppress("UNCHECKED_CAST")
    private fun bindingClass(viewType: Int): KClass<*> =
        (factories.keys.find { it.jvmName.hashCode() == viewType }
            ?: throw IllegalArgumentException("View type not found"))

    @Suppress("UNCHECKED_CAST")
    fun factory(viewType: Int): (ViewGroup) -> Layout<*> = factory(bindingClass(viewType))

    fun factory(cls: KClass<*>): (ViewGroup) -> Layout<*> =
        factories[cls]
            ?: factories.filter { it.key.isSealed }.takeIf { it.isNotEmpty() }?.filter { cls.isSubclassOf(it.key) }?.values?.firstOrNull()
            ?: throw IllegalArgumentException("Type of provided factory is incorrect")
}