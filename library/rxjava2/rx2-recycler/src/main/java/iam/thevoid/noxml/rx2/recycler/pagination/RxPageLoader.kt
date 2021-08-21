package iam.thevoid.noxml.rx2.recycler.pagination

import iam.thevoid.noxml.recycler.paging.PageLoader
import iam.thevoid.noxml.rx2.data.RxLoading
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor

abstract class RxPageLoader<PAGE_INDEX, T> : PageLoader {

    companion object {
        fun <PAGE_INDEX, T> create(
            startPage: PAGE_INDEX,
            nextPage: (PAGE_INDEX) -> PAGE_INDEX,
            loader: (PAGE_INDEX, refresh: Boolean) -> Single<PageLoader.Page<PAGE_INDEX, T>>
        ): RxPageLoader<PAGE_INDEX, T> = RxPageLoaderImpl(startPage, nextPage, loader)
    }

    /**
     * Last loaded page
     */

    protected abstract val currentPage: BehaviorProcessor<PAGE_INDEX>

    /**
     * Loading state
     */
    abstract val loading: RxLoading

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