package iam.thevoid.noxml.coroutines.recycler.extensions

import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.coroutines.extensions.view.addSetter
import iam.thevoid.noxml.recycler.DiffCallback
import iam.thevoid.noxml.recycler.StandaloneRecyclerAdapter
import iam.thevoid.noxml.recycler.diffCallback
import iam.thevoid.noxml.recycler.setItems
import kotlinx.coroutines.flow.Flow


fun <T : Any> RecyclerView.setItems(
    items: Flow<List<T>>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = addSetter(items) { setItems(it, itemBindings, diffCallbackFactory) }

inline fun <T : Any, reified A : StandaloneRecyclerAdapter<T>> RecyclerView.setItems(
    items: Flow<List<T>>,
    itemBindings: ItemBindings,
    crossinline adapterFactory: (List<T>) -> A? = { null },
    noinline diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = addSetter(items) { setItems( it, itemBindings, adapterFactory, diffCallbackFactory) }

