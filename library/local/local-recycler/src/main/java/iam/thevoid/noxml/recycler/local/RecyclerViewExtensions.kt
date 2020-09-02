@file:Suppress("unused")

package iam.thevoid.noxml.recycler.local

import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.noxml.core.local.tools.lazyDelegate
import iam.thevoid.noxml.recycler.local.delegate.EndlessScrollDelegate
import iam.thevoid.noxml.recycler.local.delegate.OnRecyclerScrollDelegate


// Duct tape around paging. Line do effectively nothing, just trigger endless scroll listener
// for case when it doesn't triggers automatically (page is small and items not cover screen full
// height)
val RecyclerView.endlessScrollDelegate
    get() = lazyDelegate(R.id.recyclerEndlessScroll, ::EndlessScrollDelegate, ::addOnScrollListener)

fun RecyclerView.triggerEndlessScroll() {
    endlessScrollDelegate.onScrolled(this, 0, 0)
}

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

val RecyclerView.startSpacing
    get() = lazyDelegate(
        R.id.recyclerStartSpacing,
        ::StartEndPaddingRecyclerDecoration,
        ::addItemDecoration
    )

fun RecyclerView.setStartSpacing(spacing: Int) {
    val decoration = startSpacing
    removeItemDecoration(decoration)
    decoration.start = spacing
    addItemDecoration(decoration)
}

val RecyclerView.endSpacing
    get() = lazyDelegate(
        R.id.recyclerEndSpacing,
        ::StartEndPaddingRecyclerDecoration,
        ::addItemDecoration
    )

fun RecyclerView.setEndSpacing(spacing: Int) {
    val decoration = endSpacing
    removeItemDecoration(decoration)
    decoration.end = spacing
    addItemDecoration(decoration)
}


/**
 * On RecyclerView scroll reverse binding
 */

val RecyclerView.onRecyclerScroll
    get() = lazyDelegate(R.id.recyclerScroll, ::OnRecyclerScrollDelegate, ::addOnScrollListener)