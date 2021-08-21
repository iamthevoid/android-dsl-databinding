package iam.thevoid.noxml.coroutines.recycler.pagination

import iam.thevoid.noxml.coroutines.data.CoroutineBoolean
import iam.thevoid.noxml.coroutines.data.CoroutineItem
import iam.thevoid.noxml.recycler.paging.PageLoader
import kotlinx.coroutines.flow.Flow

abstract class CoroutinesPageLoader<PAGE_INDEX, T> : PageLoader {

    companion object {
        fun <PAGE_INDEX, T> create(
            startPage: PAGE_INDEX,
            nextPage: (PAGE_INDEX) -> PAGE_INDEX,
            loader: suspend (PAGE_INDEX, refresh: Boolean) -> PageLoader.Page<PAGE_INDEX, T>
        ): CoroutinesPageLoader<PAGE_INDEX, T> =
            CoroutinesPageLoaderImpl(startPage, nextPage, loader)
    }

    /**
     * Last loaded page
     */

    protected abstract val currentPage: CoroutineItem<PAGE_INDEX>

    /**
     * Loading state
     */
    abstract val loading: CoroutineBoolean

    /**
     * Received items
     */
    abstract val pages: Flow<List<PageLoader.Page<PAGE_INDEX, T>>>

    /**
     * Triggers when needs to load more
     */
    abstract fun loadMore()

    /**
     * Triggers when need to reload
     */
    abstract fun refresh()
}