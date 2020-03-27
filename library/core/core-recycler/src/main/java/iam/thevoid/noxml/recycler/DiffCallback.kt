package iam.thevoid.noxml.recycler

import androidx.recyclerview.widget.DiffUtil
import iam.thevoid.e.safe

abstract class DiffCallback<T>(private val oldItems: List<T>, private val newItems: List<T>) :
    DiffUtil.Callback() {

    companion object {
        inline fun <reified T> pairOfNonNull(first: Any, second: Any) =
            if (first !is T || second !is T) null else Pair(
                first as? T,
                second as? T
            ).let { (f, s) ->
                if (f != null && s != null) Pair(f, s) else null
            }
    }

    override fun getOldListSize(): Int = oldItems.size
    override fun getNewListSize(): Int = newItems.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        areContentsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

    interface Diffable {
        fun areItemsTheSame(another: Any?): Boolean
        fun areContentsTheSame(another: Any?): Boolean
    }

    abstract class SimpleDiffable :
        Diffable {
        override fun areContentsTheSame(another: Any?): Boolean = areItemsTheSame(another)
    }

    abstract class AlwaysDiffContent :
        Diffable {
        override fun areItemsTheSame(another: Any?): Boolean =
            another?.let { it::class == this::class }.safe()

        override fun areContentsTheSame(another: Any?): Boolean = false
    }
}

fun <T : Any> diffCallback(): ((List<T>, List<T>) -> DiffCallback<T>) = { old, new ->
    object : DiffCallback<T>(old, new) {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            areItemsTheSame(oldItem, newItem) { f, s -> f.areItemsTheSame(s) }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            areItemsContentsTheSame(oldItem, newItem)
    }
}

fun <T : Any> areListsTheSame(list1: List<T>?, list2: List<T>?) =
    list1.safe().areListItemsContentsTheSame(list2.safe())

fun <T : Any> areItemsContentsTheSame(item1: T?, item2: T?): Boolean =
    areItemsTheSame(item1, item2) { f, s -> f.areContentsTheSame(s) }

fun <T : Any> areItemsTheSame(
    item1: T?,
    item2: T?,
    diffablePredicate: (DiffCallback.Diffable, Any) -> Boolean
): Boolean {
    if (item1 == null && item2 == null)
        return true

    if (item1 == null || item2 == null)
        return false

    if (item1 is DiffCallback.Diffable)
        return diffablePredicate(item1, item2)

    if (item2 is DiffCallback.Diffable)
        return diffablePredicate(item2, item1)

    return item1 == item2
}

fun <T : Any> List<T>.areListItemsContentsTheSame(list: List<T>): Boolean {
    if (isEmpty() && list.isEmpty())
        return true

    if (size != list.size)
        return false

    if (first()::class != list.first()::class)
        return false

    return when (first()) {
        is DiffCallback.Diffable ->
            all { item -> list.find { areItemsContentsTheSame(item, it) } != null } &&
                    list.all { item -> find { areItemsContentsTheSame(item, it) } != null }
        else ->
            all { list.contains(it) } && list.all { contains(it) }
    }
}