package iam.thevoid.noxml.recycler.delegate

import androidx.recyclerview.widget.RecyclerView

class OnRecyclerScrollDelegate : RecyclerView.OnScrollListener() {

    private val onScrolled by lazy { mutableListOf<RecyclerView.OnScrollListener>() }
    private val onScrollStateChanged by lazy { mutableListOf<RecyclerView.OnScrollListener>() }

    fun addOnScrolled(listener : RecyclerView.OnScrollListener) {
        onScrolled.add(listener)
    }

    fun addOnScrollStateChanged(listener : RecyclerView.OnScrollListener) {
        onScrollStateChanged.add(listener)
    }

    fun removeScrolled(listener : RecyclerView.OnScrollListener) {
        onScrolled.remove(listener)
    }

    fun removeOnScrollStateChanged(listener : RecyclerView.OnScrollListener) {
        onScrollStateChanged.remove(listener)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        onScrolled.forEach { it.onScrolled(recyclerView, dx, dy) }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        onScrollStateChanged.forEach { it.onScrollStateChanged(recyclerView, newState) }
    }
}