@file:Suppress("unused")

package iam.thevoid.noxml.rx2.recycler.extensions

import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.recycler.*
import iam.thevoid.noxml.recycler.change.OnScrollStateChanged
import iam.thevoid.noxml.recycler.change.OnScrolled
import iam.thevoid.noxml.recycler.local.onRecyclerScroll
import iam.thevoid.noxml.rx2.data.fields.RxInt
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor


fun <T : Any> RecyclerView.setItems(
    items: Flowable<out List<T>>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = addSetter(items) { setItems(it, itemBindings, diffCallbackFactory) }

inline fun <T : Any, reified A : StandaloneRecyclerAdapter<T>> RecyclerView.setItems(
    items: Flowable<out List<T>>,
    itemBindings: ItemBindings,
    crossinline adapterFactory: (List<T>) -> A? = { null },
    noinline diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = addSetter(items) { setItems(it, itemBindings, adapterFactory, diffCallbackFactory) }

/**
 * Spacing bindings
 */

fun RecyclerView.setStartSpacing(spacing: Flowable<Int>) =
    addSetter(spacing.distinctUntilChanged()) { setStartSpacing(it) }

fun RecyclerView.setEndSpacing(spacing: Flowable<Int>) =
    addSetter(spacing) { setEndSpacing(it) }

/**
 * GETTER
 */

/**
 * onRecyclerScrolled
 */

fun RecyclerView.onRecyclerScrolled(onScroll: FlowableProcessor<OnScrolled>) =
    onRecyclerScrolled(onScroll, mapper = { it })

fun <T : Any> RecyclerView.onRecyclerScrolled(
    onScroll: FlowableProcessor<T>,
    mapper: (OnScrolled) -> T
) = addSetter(Flowable.create<T>({
    val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) =
            it.onNext(mapper(OnScrolled(recyclerView, dx, dy)))
    }
    onRecyclerScroll.addOnScrolled(listener)
    it.setCancellable { onRecyclerScroll.removeScrolled(listener) }
}, BackpressureStrategy.LATEST).doOnNext(onScroll::onNext))


/**
 * onScrollStateChanged
 */

fun RecyclerView.onScrollStateChanged(onScrollStateChanged: FlowableProcessor<OnScrollStateChanged>) =
    onScrollStateChanged(onScrollStateChanged, mapper = { it })

fun <T : Any> RecyclerView.onScrollStateChanged(
    onScroll: FlowableProcessor<T>,
    mapper: (OnScrollStateChanged) -> T
) = addSetter(Flowable.create<T>({
    val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) =
            it.onNext(mapper(OnScrollStateChanged(recyclerView, newState)))
    }
    onRecyclerScroll.addOnScrollStateChanged(listener)
    it.setCancellable { onRecyclerScroll.removeOnScrollStateChanged(listener) }
}, BackpressureStrategy.LATEST).doOnNext(onScroll::onNext))