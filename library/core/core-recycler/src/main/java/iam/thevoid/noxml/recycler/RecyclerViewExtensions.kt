@file:Suppress("unused")

package iam.thevoid.noxml.recycler

import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.recycler.local.*

@Suppress("UNCHECKED_CAST", "NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
fun <T : Any> RecyclerView.setItems(
    items: List<T>,
    bindings: ItemBindings,
    callbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) {
    (adapter as? StandaloneRecyclerAdapter<T>)?.apply {
        data = items.toMutableList()
    } ?: run {
        StandaloneRecyclerAdapter(items).apply {
            this@apply.bindings = bindings
            this@apply.diffCallbackFactory = callbackFactory
            adapter = this
        }
    }
    triggerEndlessScroll()
}

@Suppress("UNCHECKED_CAST", "NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
inline fun <T : Any, reified A : StandaloneRecyclerAdapter<T>> RecyclerView.setItems(
    items: List<T>,
    itemBindings: ItemBindings,
    foreignAdapterFactory: (List<T>) -> A? = { null },
    noinline diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) {
    (adapter as? StandaloneRecyclerAdapter<T>)?.apply {
        data = items.toMutableList()
    } ?: run {
        (foreignAdapterFactory(items) ?: StandaloneRecyclerAdapter(items)).apply {
            this@apply.bindings = itemBindings
            this@apply.diffCallbackFactory = diffCallbackFactory
            adapter = this
        }
    }

    triggerEndlessScroll()
}

// Duct tape around paging. Line do effectively nothing, just trigger endless scroll listener
// for case when it doesn't triggers automatically (page is small and items not cover screen full
// height)

fun RecyclerView.resetEndlessScrollState() {
    endlessScrollDelegate.resetListeners()
}

fun RecyclerView.setLoadMore(action: (Int) -> Unit) {
    endlessScrollDelegate.addListener(object : EndlessScrollListener() {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
            action(page)
        }
    })
}

/**
 * Spacing bindings
 */

fun RecyclerView.setStartSpacing(spacing: Int) {
    val decoration = startSpacing
    removeItemDecoration(decoration)
    decoration.start = spacing
    addItemDecoration(decoration)
}

fun RecyclerView.setEndSpacing(spacing: Int) {
    val decoration = endSpacing
    removeItemDecoration(decoration)
    decoration.end = spacing
    addItemDecoration(decoration)
}