package iam.thevoid.noxml.rx2.adapterview

import android.view.ViewGroup
import com.jakewharton.rx.ReplayingShare
import iam.thevoid.noxml.adapterview.Layout
import io.reactivex.Flowable
import iam.thevoid.noxml.rx2.data.fields.RxField

abstract class RxLayout<T>( parent: ViewGroup) : Layout<T>(parent)  {

    private val itemFactory: () -> RxField<T> = { RxField() }

    override fun set(item: T) {
        this.item.set(item)
    }

    val item: RxField<T> by lazy(itemFactory)

    val itemChanges: Flowable<T> by lazy {
        item.onlyPresent().compose(ReplayingShare.instance())
    }

    fun changeItem(itemChange: T.() -> Unit) {
        item.set(item.get()?.apply {
            itemChange()
        })
    }
}