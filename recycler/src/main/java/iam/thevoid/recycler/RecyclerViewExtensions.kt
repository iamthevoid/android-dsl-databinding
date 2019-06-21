package iam.thevoid.recycler

import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Flowable
import thevoid.iam.components.rx.fields.RxList
import thevoid.iam.components.widget.ext.addSetter

fun <T : Any> RecyclerView.setItems(
    items: RxList<T>,
    itemBindings: RxAdapter.ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>)? = null
) = addSetter(items.observe()) { setItems(it, itemBindings, diffCallbackFactory) }

fun <T : Any> RecyclerView.setItems(
    itemsFlowable: Flowable<List<T>>,
    itemBindings: RxAdapter.ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>)? = null
) = addSetter(itemsFlowable) { setItems(it, itemBindings, diffCallbackFactory) }

private fun <T : Any> RecyclerView.setItems(
    items : List<T>,
    itemBindings: RxAdapter.ItemBindings,
    diffCallbackFactory: ((old: List<T>, new: List<T>) -> DiffCallback<T>)? = null
) {
    (adapter as? RxAdapter<T>)?.apply {
        data = items.toMutableList()
    } ?: run {
        RxAdapter(items).apply {
            this@apply.bindings = itemBindings
            this@apply.diffCallbackFactory = diffCallbackFactory
            adapter = this
        }
    }
}