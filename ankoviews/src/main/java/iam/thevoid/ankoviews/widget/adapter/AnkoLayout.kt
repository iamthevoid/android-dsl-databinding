package iam.thevoid.ankoviews.widget.adapter

import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import thevoid.iam.components.mvvm.adapter.Layout

abstract class AnkoLayout<T>(parent: ViewGroup) : Layout<T>(parent), AnkoComponent<AnkoLayout<T>> {
    override fun createView(parent: ViewGroup): View =
        createView(AnkoContext.create(parent.context, this, false))
}