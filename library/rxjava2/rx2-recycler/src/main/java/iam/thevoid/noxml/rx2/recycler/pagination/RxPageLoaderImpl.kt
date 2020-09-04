package iam.thevoid.noxml.rx2.recycler.pagination

import android.util.Log
import iam.thevoid.noxml.recycler.paging.PageLoader
import iam.thevoid.noxml.rx2.data.RxLoading
import iam.thevoid.noxml.rx2.utils.loading
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicBoolean

internal class RxPageLoaderImpl<PAGE_INDEX, T>(
    private val startPage: PAGE_INDEX,
    private val nextPage: (PAGE_INDEX) -> PAGE_INDEX,
    private val loader: (PAGE_INDEX, refresh: Boolean) -> Single<PageLoader.Page<PAGE_INDEX, T>>
) : RxPageLoader<PAGE_INDEX, T>() {

    companion object {
        val TAG = RxPageLoaderImpl::class.java.simpleName
    }

    private val isLastPage: AtomicBoolean = AtomicBoolean()

    private var disposable: Disposable? = null

    private val loaded: BehaviorProcessor<List<PageLoader.Page<PAGE_INDEX, T>>> =
        BehaviorProcessor.createDefault(emptyList())

    private fun setPage(page: PageLoader.Page<PAGE_INDEX, T>) = loaded.onNext(listOf(page))

    private fun addPage(page: PageLoader.Page<PAGE_INDEX, T>) = loaded.apply {
        onNext(value.orEmpty().toMutableList().apply { add(page) })
    }

    /**
     * loading state
     */
    override val loading = RxLoading()

    /**
     * Last loaded page
     */

    override val currentPage: BehaviorProcessor<PAGE_INDEX> =
        BehaviorProcessor.createDefault(startPage)

    /**
     * Received items
     */
    override val pages: Flowable<List<PageLoader.Page<PAGE_INDEX, T>>> by lazy {
        loaded.doOnSubscribe {
            if (loaded.value.isNullOrEmpty()) {
                load(startPage, false)
            }
        }
    }

    override fun refresh() {
        disposable = load(startPage, true)
    }

    override fun loadMore() {
        disposable = load(nextPage(currentPage.value!!), false)
    }

    private fun load(page: PAGE_INDEX, refresh: Boolean): Disposable {
        if (!refresh && isLastPage.get())
            return Completable.complete().subscribe()

        return loader(page, refresh)
            .loading(loading)
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                when {
                    refresh -> setPage(response)
                    else -> addPage(response)
                }
                currentPage.onNext(response.pageIndex)
                isLastPage.set(response.isLastPage)
            }, { Log.e(TAG, "Loading page failed", it) })
    }
}