package iam.thevoid.noxml.rx2.recycler.extensions

import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.recycler.DiffCallback
import iam.thevoid.noxml.recycler.StandaloneRecyclerAdapter
import iam.thevoid.noxml.recycler.change.OnScrollStateChanged
import iam.thevoid.noxml.recycler.change.OnScrolled
import iam.thevoid.noxml.recycler.diffCallback
import iam.thevoid.noxml.recycler.local.onRecyclerScroll
import iam.thevoid.noxml.recycler.setItems
import iam.thevoid.noxml.rx2.data.fields.RxField
import iam.thevoid.noxml.rx2.data.fields.RxInt
import iam.thevoid.noxml.rx2.data.fields.RxList
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import iam.thevoid.noxml.rx2.recycler.R
import iam.thevoid.noxml.rx2.recycler.pagination.OldPaginationLoader
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Pagination
 */

@Deprecated("Will be removed in release version")
private var RecyclerView.oldPaginationLoader
    private set(loader) = setTag(R.id.recyclerPaginatedLoader, loader)
    get() = (getTag(R.id.recyclerPaginatedLoader) as? OldPaginationLoader<*>)


@Deprecated("Will be removed in release version")
fun <T : Any> RecyclerView.setPaginationLoader(
    firstPage: Int,
    loader: (Int) -> Single<out OldPaginationLoader.Response<T>>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = setPaginationLoader(
    OldPaginationLoader(
        firstPage,
        loader
    ), itemBindings, diffCallbackFactory
)

@Deprecated("Will be removed in release version")
fun <T : Any> RecyclerView.setPaginationLoader(
    pageLoader: OldPaginationLoader<T>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) {
    if (oldPaginationLoader != null)
        return
    setItems(pageLoader.observe(), itemBindings, diffCallbackFactory).also {
        addOnScrollListener(pageLoader)
        oldPaginationLoader = pageLoader
    }
}

@Deprecated("Will be removed in release version")
fun RecyclerView.reloadFirstPage(trigger: Flowable<Any>) =
    addSetter(trigger) { reloadFirstPage() }

@Deprecated("Will be removed in release version")
fun RecyclerView.reloadFirstPage() {
    oldPaginationLoader?.loadFirst()
}

/**
 * Fields and items
 */

fun RecyclerView.setStartSpacing(spacing: RxInt) =
    setStartSpacing(spacing.observe())

fun RecyclerView.setEndSpacing(spacing: RxInt) =
    setEndSpacing(spacing.observe())

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun <T : Any> RecyclerView.setItems(
    items: RxList<T>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = addSetter(items.observe()) { setItems(it, itemBindings, diffCallbackFactory) }

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
inline fun <T : Any, reified A : StandaloneRecyclerAdapter<T>> RecyclerView.setItems(
    items: RxList<T>,
    itemBindings: ItemBindings,
    crossinline adapterFactory: (List<T>) -> A? = { null },
    noinline diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = addSetter(items.observe()) { setItems(it, itemBindings, adapterFactory, diffCallbackFactory) }

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun RecyclerView.onRecyclerScrolled(rxOnScroll: RxField<OnScrolled>) =
    onRecyclerScrolled(rxOnScroll) { it }

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun <T : Any> RecyclerView.onRecyclerScrolled(onScroll: RxField<T>, mapper: (OnScrolled) -> T) =
    addSetter(Flowable.create<T>({
        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                it.onNext(mapper(OnScrolled(recyclerView, dx, dy)))
            }
        }
        onRecyclerScroll.addOnScrolled(listener)
        it.setCancellable { onRecyclerScroll.removeScrolled(listener) }
    }, BackpressureStrategy.LATEST).doOnNext(onScroll::set))

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun RecyclerView.onScrollStateChanged(rxOnScroll: RxField<OnScrollStateChanged>) =
    onRecyclerScrollStateChanged(rxOnScroll) { it }

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
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