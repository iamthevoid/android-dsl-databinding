package iam.thevoid.noxml.anko.rx

import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import iam.thevoid.noxml.rx.adapterview.RxLayout


abstract class AnkoRxLayout<T>(parent: ViewGroup) : RxLayout<T>(parent),
    AnkoComponent<AnkoRxLayout<T>> {
    override fun createView(parent: ViewGroup): View =
        createView(
            AnkoContext.create(
                parent.context,
                this,
                false
            )
        )
}