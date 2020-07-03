package iam.thevoid.noxml.recycler

import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.noxml.adapterview.Layout
import iam.thevoid.noxml.extensions.view.bindDataImmediate
import iam.thevoid.noxml.recycler.util.isCallingFromMatcher
import iam.thevoid.noxml.recycler.util.isRunningTest
import iam.thevoid.noxml.recycler.util.onItAndChildrenTree

class Holder<T>(private val layout: Layout<T>) : RecyclerView.ViewHolder(layout.view) {

    fun onBind(item: T) = layout.apply {
        this@apply.set(item)
        if (isRunningTest() && isCallingFromMatcher())
            layout.view.onItAndChildrenTree { bindDataImmediate() }
    }
}