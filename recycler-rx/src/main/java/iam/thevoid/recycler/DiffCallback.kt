package iam.thevoid.recycler

import androidx.recyclerview.widget.DiffUtil
import iam.thevoid.e.safe

abstract class DiffCallback<T>(private val oldItems: List<T>, private val newItems: List<T>) : DiffUtil.Callback() {

    companion object {
        inline fun <reified T> pairOfNonNull(first: Any, second: Any) =
            if (first !is T || second !is T) null else Pair(first as? T, second as? T).let { (f, s) ->
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

    abstract class SimpleDiffable : Diffable {
        override fun areContentsTheSame(another: Any?): Boolean = areItemsTheSame(another)
    }

    abstract class AlwaysDiffContent : Diffable {
        override fun areItemsTheSame(another: Any?): Boolean =
            another?.let { it::class == this::class }.safe()

        override fun areContentsTheSame(another: Any?): Boolean = false
    }
}