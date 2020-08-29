package iam.thevoid.noxml.rx2.recycler.pagination

import iam.thevoid.noxml.recycler.paging.PageLoader
import iam.thevoid.noxml.rx2.data.RxLoading
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

abstract class RxPageLoader<PAGE_INDEX, T> : PageLoader {

    /**
     * Loading state
     */
    protected abstract val loading: RxLoading

    /**
     * Last loaded page
     */

    protected abstract val currentPage: BehaviorProcessor<PAGE_INDEX>

    /**
     * Received items
     */
    abstract val pages: Flowable<List<PageLoader.Page<PAGE_INDEX, T>>>

    /**
     * Triggers when needs to load more
     */
    abstract fun loadMore()

    /**
     * Triggers when need to reload
     */
    abstract fun refresh()
}