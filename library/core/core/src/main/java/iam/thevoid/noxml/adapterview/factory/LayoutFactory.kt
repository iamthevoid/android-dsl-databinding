package iam.thevoid.noxml.adapterview.factory

import android.view.ViewGroup
import iam.thevoid.noxml.adapterview.Layout

interface LayoutFactory<T> {
    fun createLayout(viewGroup: ViewGroup) : Layout<T>
}