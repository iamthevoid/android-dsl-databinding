package iam.thevoid.noxml.anko.coroutines

import android.view.View
import android.view.ViewGroup
import iam.thevoid.noxml.adapterview.Layout
import iam.thevoid.noxml.coroutines.CoroutinesLayout
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext


abstract class AnkoCoroutinesLayout<T>(parent: ViewGroup) : CoroutinesLayout<T>(parent), AnkoComponent<AnkoCoroutinesLayout<T>> {
    override fun createView(parent: ViewGroup): View =
        createView(AnkoContext.create(parent.context, this, false))
}