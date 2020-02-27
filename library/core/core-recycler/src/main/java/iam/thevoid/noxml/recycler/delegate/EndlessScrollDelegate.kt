package iam.thevoid.noxml.recycler.delegate

import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.noxml.recycler.EndlessScrollListener

class EndlessScrollDelegate : EndlessScrollListener() {

    val listeners = mutableListOf<EndlessScrollListener>()

    fun addListener(listener: EndlessScrollListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: EndlessScrollListener) {
        listeners.remove(listener)
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) =
        listeners.forEach { it.onLoadMore(page, totalItemsCount, view) }

    fun resetListeners() {
        resetState()
        listeners.forEach { it.resetState() }
    }

}