package iam.thevoid.noxml.rx.recycler.extensions

import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.recycler.*
import iam.thevoid.noxml.recycler.change.OnScrollStateChanged
import iam.thevoid.noxml.recycler.change.OnScrolled
import io.reactivex.Flowable
import io.reactivex.Single
import iam.thevoid.noxml.rx.data.fields.RxField
import iam.thevoid.noxml.rx.data.fields.RxInt
import iam.thevoid.noxml.rx.data.fields.RxList
import iam.thevoid.noxml.rx.extensions.addSetter
import iam.thevoid.noxml.rx.recycler.pagination.OldPaginationLoader

fun <T : Any> RecyclerView.setItems(
    itemsFlowable: Flowable<out List<T>>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = addSetter(itemsFlowable) { setItems(it, itemBindings, diffCallbackFactory) }

inline fun <T : Any, reified A : StandaloneRecyclerAdapter<T>> RecyclerView.setItems(
    itemsFlowable: Flowable<List<T>>,
    itemBindings: ItemBindings,
    crossinline adapterFactory: (List<T>) -> A? = { null },
    noinline diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = addSetter(itemsFlowable) { setItems( it, itemBindings, adapterFactory, diffCallbackFactory) }

fun <T : Any> RecyclerView.setItems(
    items: RxList<T>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = addSetter(items.observe()) { setItems(it, itemBindings, diffCallbackFactory) }

inline fun <T : Any, reified A : StandaloneRecyclerAdapter<T>> RecyclerView.setItems(
    items: RxList<T>,
    itemBindings: ItemBindings,
    crossinline adapterFactory: (List<T>) -> A? = { null },
    noinline diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = addSetter(items.observe()) { setItems(it, itemBindings, adapterFactory, diffCallbackFactory) }

private var RecyclerView.paginationLoader
    private set(loader) = setTag(R.id.recyclerPaginatedLoader, loader)
    get() = (getTag(R.id.recyclerPaginatedLoader) as? OldPaginationLoader<*>)

private val RecyclerView.endlessScrollDelegate
    get() = getTag(R.id.recyclerEndlessScroll) as? iam.thevoid.noxml.recycler.delegate.EndlessScrollDelegate
        ?: iam.thevoid.noxml.recycler.delegate.EndlessScrollDelegate().also {
            setTag(R.id.recyclerEndlessScroll, it)
            addOnScrollListener(it)
        }


@Deprecated("")
fun <T : Any> RecyclerView.setPaginationLoader(
    firstPage: Int,
    loader: (Int) -> Single<out OldPaginationLoader.Response<T>>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = setPaginationLoader(
    OldPaginationLoader(
        firstPage,
        loader
    ), itemBindings, diffCallbackFactory)

@Deprecated("")
fun <T : Any> RecyclerView.setPaginationLoader(
    pageLoader: OldPaginationLoader<T>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) {
    if (paginationLoader != null)
        return
    setItems(pageLoader.observe(), itemBindings, diffCallbackFactory).also {
        addOnScrollListener(pageLoader)
        paginationLoader = pageLoader
    }
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

@Deprecated("")
fun RecyclerView.reloadFirstPage(trigger: Flowable<Any>) =
    addSetter(trigger) { reloadFirstPage() }

@Deprecated("")
fun RecyclerView.reloadFirstPage() {
    paginationLoader?.loadFirst()
}

/**
 * Spacing bindings
 */

private val RecyclerView.startSpacing
    get() = (getTag(R.id.recyclerStartSpacing) as? StartEndPaddingRecyclerDecoration)
        ?: StartEndPaddingRecyclerDecoration().also {
            addItemDecoration(it)
            setTag(R.id.recyclerStartSpacing, it)
        }

fun RecyclerView.setStartSpacing(spacing: RxInt) =
    setStartSpacing(spacing.observe())

fun RecyclerView.setStartSpacing(spacing: Flowable<Int>) =
    addSetter(spacing.distinctUntilChanged()) { setStartSpacing(it) }

fun RecyclerView.setStartSpacing(spacing: Int) {
    val decoration = startSpacing
    removeItemDecoration(decoration)
    decoration.start = spacing
    addItemDecoration(decoration)
}

private val RecyclerView.endSpacing
    get() = (getTag(R.id.recyclerEndSpacing) as? StartEndPaddingRecyclerDecoration)
        ?: StartEndPaddingRecyclerDecoration().also {
            addItemDecoration(it)
            setTag(R.id.recyclerEndSpacing, it)
        }

fun RecyclerView.setEndSpacing(spacing: RxInt) =
    setEndSpacing(spacing.observe())

fun RecyclerView.setEndSpacing(spacing: Flowable<Int>) =
    addSetter(spacing) { setEndSpacing(it) }

fun RecyclerView.setEndSpacing(spacing: Int) {
    val decoration = endSpacing
    removeItemDecoration(decoration)
    decoration.end = spacing
    addItemDecoration(decoration)
}


/**
 * On RecyclerView scroll reverse binding
 */

private val RecyclerView.onRecyclerScroll
    get() = (getTag(R.id.recyclerScroll) as? iam.thevoid.noxml.recycler.delegate.OnRecyclerScrollDelegate) ?: iam.thevoid.noxml.recycler.delegate.OnRecyclerScrollDelegate()
        .also {
            setTag(R.id.recyclerScroll, it)
            addOnScrollListener(it)
        }


fun RecyclerView.onRecyclerScrolled(rxOnScroll: RxField<OnScrolled>) =
    onRecyclerScrolled(rxOnScroll) { it }

fun <T : Any> RecyclerView.onRecyclerScrolled(onScroll: RxField<T>, mapper: (OnScrolled) -> T) {
    onRecyclerScroll.addOnScrolled(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            onScroll.set(mapper(OnScrolled(recyclerView, dx, dy)))
        }
    })
}


fun RecyclerView.onScrollStateChanged(rxOnScroll: RxField<OnScrollStateChanged>) =
    onRecyclerScrollStateChanged(rxOnScroll) { it }

fun <T : Any> RecyclerView.onRecyclerScrollStateChanged(
    onScroll: RxField<T>,
    mapper: (OnScrollStateChanged) -> T
) {
    onRecyclerScroll.addOnScrolled(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            onScroll.set(mapper(OnScrollStateChanged(recyclerView, newState)))
        }
    })
}