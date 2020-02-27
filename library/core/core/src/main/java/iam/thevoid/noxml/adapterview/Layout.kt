package iam.thevoid.noxml.adapterview

import android.content.Context
import android.view.View
import android.view.ViewGroup

abstract class Layout<T>(private val parent: ViewGroup) {

    val view by lazy { createView(parent) }

    val context: Context
        get() = parent.context

    abstract fun set(item : T)

    abstract fun createView(parent: ViewGroup): View

}