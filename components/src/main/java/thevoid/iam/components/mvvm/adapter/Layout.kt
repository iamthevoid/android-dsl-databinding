package thevoid.iam.components.mvvm.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import thevoid.iam.components.rx.fields.RxField

abstract class Layout<T>(parent: ViewGroup) {

    private val itemFactory: () -> RxField<T> = { RxField() }

    val view by lazy { createView(parent) }

    val context : Context
        get() = view.context

    val item: RxField<T> by lazy(itemFactory)

    abstract fun createView(parent: ViewGroup): View

    fun changeItem(itemChange : T.() -> Unit) {
        item.set(item.get()?.apply {
            itemChange()
        })
    }
}