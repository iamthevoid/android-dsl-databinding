package iam.thevoid.noxml.coroutines.recycler.pagination

import iam.thevoid.noxml.coroutines.data.CoroutineBoolean
import iam.thevoid.noxml.coroutines.data.CoroutineItem
import iam.thevoid.noxml.recycler.paging.PageLoader
import kotlinx.coroutines.flow.Flow

interface CoroutinesPageLoader<PAGE_INDEX, T> : PageLoader {

    /**
     * Loading state
     */
    val loading: CoroutineBoolean

    /**
     * Last loaded page
     */

    val currentPage: CoroutineItem<PAGE_INDEX>

    /**
     * Received items
     */
    val pages: Flow<List<PageLoader.Page<PAGE_INDEX, T>>>

    /**
     * Triggers when needs to load more
     */
    fun loadMore()

    /**
     * Triggers when need to reload
     */
    fun refresh()
}