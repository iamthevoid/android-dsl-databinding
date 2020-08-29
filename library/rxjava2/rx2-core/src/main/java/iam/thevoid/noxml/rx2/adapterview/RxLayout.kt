package iam.thevoid.noxml.rx2.adapterview

import android.view.ViewGroup
import com.jakewharton.rx.ReplayingShare
import iam.thevoid.noxml.adapterview.Layout
import io.reactivex.Flowable
import iam.thevoid.noxml.rx2.data.fields.RxField
import io.reactivex.processors.BehaviorProcessor

abstract class RxLayout<T>( parent: ViewGroup) : Layout<T>(parent)  {

    override fun set(item: T) {
        this.item.onNext(item)
    }

    @Deprecated("Became private in major version, for get item use fun getItem() instead")
    val item: BehaviorProcessor<T> by lazy { BehaviorProcessor.create() }

    val itemChanges: Flowable<T> by lazy {
        item.compose(ReplayingShare.instance())
    }

    fun getItem() : T? = item.value

    fun changeItem(itemChange: T.() -> Unit) {
        getItem()?.also { item ->
            set(item.apply { itemChange() })
        }
    }
}

