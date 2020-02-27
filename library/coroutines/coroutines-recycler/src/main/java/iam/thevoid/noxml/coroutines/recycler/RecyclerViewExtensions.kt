package iam.thevoid.noxml.coroutines.recycler

import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.coroutines.extensions.addSetter
import iam.thevoid.noxml.recycler.DiffCallback
import iam.thevoid.noxml.recycler.StandaloneRecyclerAdapter
import iam.thevoid.noxml.recycler.diffCallback
import iam.thevoid.noxml.recycler.setItems
import kotlinx.coroutines.flow.Flow


fun <T : Any> RecyclerView.setItems(
    itemsFlow: Flow<List<T>>,
    itemBindings: ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = addSetter(itemsFlow) { setItems(it, itemBindings, diffCallbackFactory) }

inline fun <T : Any, reified A : StandaloneRecyclerAdapter<T>> RecyclerView.setItems(
    itemsFlow: Flow<List<T>>,
    itemBindings: ItemBindings,
    crossinline adapterFactory: (List<T>) -> A? = { null },
    noinline diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>) = diffCallback()
) = addSetter(itemsFlow) { setItems( it, itemBindings, adapterFactory, diffCallbackFactory) }
