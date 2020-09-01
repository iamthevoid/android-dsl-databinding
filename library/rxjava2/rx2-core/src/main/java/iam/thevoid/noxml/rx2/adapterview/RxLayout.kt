package iam.thevoid.noxml.rx2.adapterview

import android.view.ViewGroup
import iam.thevoid.noxml.adapterview.Layout
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

abstract class RxLayout<T>( parent: ViewGroup) : Layout<T>(parent)  {

    override fun set(item: T) {
        this.item.onNext(item)
    }

    @Deprecated("Became private in major version, for get item use fun getItem() instead")
    val item: BehaviorProcessor<T> by lazy { BehaviorProcessor.create() }

    val itemChanges: Flowable<T> by lazy { item }

    fun getItem() : T? = item.value

    fun changeItem(itemChange: T.() -> Unit) {
        getItem()?.also { item ->
            set(item.apply { itemChange() })
        }
    }
}

