@file:Suppress("unused")

package iam.thevoid.noxml.recycler.local

import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.noxml.recycler.local.delegate.OnRecyclerScrollDelegate
import iam.thevoid.noxml.recycler.local.delegate.EndlessScrollDelegate


// Duct tape around paging. Line do effectively nothing, just trigger endless scroll listener
// for case when it doesn't triggers automatically (page is small and items not cover screen full
// height)
fun RecyclerView.triggerEndlessScroll() {
    endlessScrollDelegate.onScrolled(this, 0, 0)
}

val RecyclerView.endlessScrollDelegate
    get() = getTag(R.id.recyclerEndlessScroll) as? EndlessScrollDelegate
        ?: EndlessScrollDelegate().also {
            setTag(R.id.recyclerEndlessScroll, it)
            addOnScrollListener(it)
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
    get() = (getTag(R.id.recyclerStartSpacing) as? StartEndPaddingRecyclerDecoration)
        ?: StartEndPaddingRecyclerDecoration().also {
            addItemDecoration(it)
            setTag(R.id.recyclerStartSpacing, it)
        }

fun RecyclerView.setStartSpacing(spacing: Int) {
    val decoration = startSpacing
    removeItemDecoration(decoration)
    decoration.start = spacing
    addItemDecoration(decoration)
}

val RecyclerView.endSpacing
    get() = (getTag(R.id.recyclerEndSpacing) as? StartEndPaddingRecyclerDecoration)
        ?: StartEndPaddingRecyclerDecoration().also {
            addItemDecoration(it)
            setTag(R.id.recyclerEndSpacing, it)
        }

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
    get() = (getTag(R.id.recyclerScroll) as? OnRecyclerScrollDelegate)
        ?: OnRecyclerScrollDelegate()
            .also {
                setTag(R.id.recyclerScroll, it)
                addOnScrollListener(it)
            }