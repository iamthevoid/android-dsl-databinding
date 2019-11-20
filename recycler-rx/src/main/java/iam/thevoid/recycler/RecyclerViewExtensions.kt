package iam.thevoid.recycler

import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.recycler.delegate.EndlessScrollDelegate
import iam.thevoid.recycler.delegate.OnRecyclerScrollDelegate
import io.reactivex.Flowable
import io.reactivex.Single
import thevoid.iam.rx.adapter.ItemBindings
import thevoid.iam.rx.rxdata.fields.RxField
import thevoid.iam.rx.rxdata.fields.RxInt
import thevoid.iam.rx.rxdata.fields.RxList
import thevoid.iam.rx.widget.ext.addGetter
import thevoid.iam.rx.widget.ext.addSetter

fun <T : Any> RecyclerView.setItems(
    items: RxList<T>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>)? = null
) = addSetter(items.observe()) { setItems(it, itemBindings, diffCallbackFactory) }

fun <T : Any> RecyclerView.setItems(
    itemsFlowable: Flowable<out List<T>>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>)? = null
) = addSetter(itemsFlowable) { setItems(it, itemBindings, diffCallbackFactory) }

private fun <T : Any> RecyclerView.setItems(
    items: List<T>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>)? = null
) {
    (adapter as? RxRecyclerAdapter<T>)?.apply {
        data = items.toMutableList()
    } ?: run {
        RxRecyclerAdapter(items).apply {
            this@apply.bindings = itemBindings
            this@apply.diffCallbackFactory = diffCallbackFactory
            adapter = this
        }
    }
}

inline fun <T : Any, reified A : RxRecyclerAdapter<T>> RecyclerView.setItems(
    items: RxList<T>,
    itemBindings: ItemBindings,
    crossinline adapterFactory: (List<T>) -> A? = { null },
    noinline diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>)? = null
) = addSetter(items.observe()) { setItems(it, itemBindings, adapterFactory, diffCallbackFactory) }

inline fun <T : Any, reified A : RxRecyclerAdapter<T>> RecyclerView.setItems(
    itemsFlowable: Flowable<List<T>>,
    itemBindings: ItemBindings,
    crossinline adapterFactory: (List<T>) -> A? = { null },
    noinline diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>)? = null
) = addSetter(itemsFlowable) { setItems(it, itemBindings, adapterFactory, diffCallbackFactory) }


inline fun <T : Any, reified A : RxRecyclerAdapter<T>> RecyclerView.setItems(
    items: List<T>,
    itemBindings: ItemBindings,
    adapterFactory: (List<T>) -> A? = { null },
    noinline diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>)? = null
) {
    (adapter as? RxRecyclerAdapter<T>)?.apply {
        data = items.toMutableList()
    } ?: run {
        (adapterFactory(items) ?: RxRecyclerAdapter(items)).apply {
            this@apply.bindings = itemBindings
            this@apply.diffCallbackFactory = diffCallbackFactory
            adapter = this
        }
    }
}

private var RecyclerView.paginationLoader
    private set(loader) = setTag(R.id.recyclerPaginatedLoader, loader)
    get() = (getTag(R.id.recyclerPaginatedLoader) as? OldPaginationLoader<*>)

private val RecyclerView.endlessScrollDelegate
    get() = getTag(R.id.recyclerEndlessScroll) as? EndlessScrollDelegate
        ?: EndlessScrollDelegate().also {
            setTag(R.id.recyclerEndlessScroll, it)
            addOnScrollListener(it)
        }


@Deprecated("")
fun <T : Any> RecyclerView.setPaginationLoader(
    firstPage: Int,
    loader: (Int) -> Single<out OldPaginationLoader.Response<T>>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>)? = null
) = setPaginationLoader(OldPaginationLoader(firstPage, loader), itemBindings, diffCallbackFactory)

@Deprecated("")
fun <T : Any> RecyclerView.setPaginationLoader(
    pageLoader: OldPaginationLoader<T>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>)? = null
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
    get() = (getTag(R.id.recyclerScroll) as? OnRecyclerScrollDelegate) ?: OnRecyclerScrollDelegate()
        .also {
            setTag(R.id.recyclerScroll, it)
            addOnScrollListener(it)
        }


fun RecyclerView.onRecyclerScrolled(rxOnScroll: RxField<OnScrolled>) =
    onRecyclerScrolled(rxOnScroll) { it }

fun <T : Any> RecyclerView.onRecyclerScrolled(rxOnScroll: RxField<T>, mapper: (OnScrolled) -> T) =
    addGetter({
        onRecyclerScroll.addOnScrolled(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                it.invoke(mapper(OnScrolled(recyclerView, dx, dy)))
            }
        })
    }, rxOnScroll)


fun RecyclerView.onScrollStateChanged(rxOnScroll: RxField<OnScrollStateChanged>) =
    onRecyclerScrollStateChanged(rxOnScroll) { it }

fun <T : Any> RecyclerView.onRecyclerScrollStateChanged(
    rxOnScroll: RxField<T>,
    mapper: (OnScrollStateChanged) -> T
) =
    addGetter({
        onRecyclerScroll.addOnScrolled(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                it.invoke(mapper(OnScrollStateChanged(recyclerView, newState)))
            }
        })
    }, rxOnScroll)


data class OnScrolled(val recyclerView: RecyclerView, val dx: Int, val dy: Int)

data class OnScrollStateChanged(val recyclerView: RecyclerView, val state: Int)