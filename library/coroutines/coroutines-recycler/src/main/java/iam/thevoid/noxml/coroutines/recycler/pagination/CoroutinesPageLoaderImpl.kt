package iam.thevoid.noxml.coroutines.recycler.pagination

import iam.thevoid.noxml.coroutines.data.CoroutineBoolean
import iam.thevoid.noxml.coroutines.data.CoroutineItem
import iam.thevoid.noxml.coroutines.data.CoroutineList
import iam.thevoid.noxml.recycler.paging.PageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

abstract class CoroutinesPageLoaderImpl<PAGE, T>(
        private val startPage: PAGE,
        private val nextPage: (PAGE) -> PAGE,
        private val loader: suspend (PAGE, refresh : Boolean) -> PageLoader.Response<PAGE, T>
) : CoroutinesPageLoader<PAGE, T>, CoroutineScope by CoroutineScope(Dispatchers.IO) {

    private val isLastPage: AtomicBoolean = AtomicBoolean()

    private var loadingJob: Job? = null

    private val loaded: CoroutineList<T> = CoroutineList()

    fun addItems(items: List<T>) = loaded.add(items)

    fun setItems(items: List<T>) = loaded.set(items)

    /**
     * loading state
     */
    override val loading = CoroutineBoolean()

    /**
     * Last loaded page
     */

    override val currentPage: CoroutineItem<PAGE> = CoroutineItem(startPage)

    /**
     * Received items
     */
    override val items: Flow<List<T>> by lazy {
        loaded.observe().onStart {
            if (loaded.isEmpty()) {
                load(startPage, false)
            }
        }
    }

    override fun refresh() {
        loadingJob = launch { load(startPage, true) }
    }

    override fun loadMore() {
        loadingJob = launch { load(nextPage(currentPage.get()), false) }
    }

    private suspend fun load(page: PAGE, refresh: Boolean) {

        if (!refresh && isLastPage.get())
            return

        loading.set(true)
        val response = loader(page, refresh)
        loading.set(false)

        when {
            refresh -> setItems(response.items)
            else -> addItems(response.items)
        }

        currentPage.set(response.page)
        isLastPage.set(response.isLastPage)
    }
}