package iam.thevoid.noxml.rx2.recycler.pagination

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.e.safe
import iam.thevoid.noxml.recycler.local.EndlessScrollListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.atomic.AtomicBoolean

class OldPaginationLoader<T>(
    private val startPage: Int,
    val loader: (Int) -> Single<out Response<T>>
) :
    EndlessScrollListener() {

    private val subject: BehaviorSubject<List<T>> by lazy { BehaviorSubject.createDefault(emptyList<T>()) }

    private val lastPage by lazy { AtomicBoolean() }

    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
        if (!lastPage.get())
            loadMore(page)
    }

    fun observe() = subject.toFlowable(BackpressureStrategy.LATEST)
        .doOnSubscribe { loadMore(startPage) }

    fun loadFirst() {
        loadMore(startPage)
    }

    @SuppressLint("CheckResult")
    private fun loadMore(page: Int) {
        loader(page)
            .doOnSubscribe { if (page == startPage) resetState() }
            .subscribeOn(Schedulers.io())
            .subscribe({
                lastPage.set(it.isLastPage)
                subject.apply {
                    onNext(mutableListOf<T>().apply {
                        if (page != startPage)
                            addAll(value.safe())
                        addAll(it.items)
                    })
                }
            }, { Log.e("PaginationLoader", "Can not load page $page", it) })
    }


    open class Response<T>(val items: List<T>, val isLastPage: Boolean)
}
