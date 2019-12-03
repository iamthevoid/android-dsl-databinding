package iam.thevoid.recycler

import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.recycler.util.isCallingFromMatcher
import iam.thevoid.recycler.util.isRunningTest
import iam.thevoid.recycler.util.onItAndChildrenTree
import thevoid.iam.rx.adapter.Layout
import thevoid.iam.rx.widget.ext.fakeAttachToWindow

class Holder<T>(private val layout: Layout<T>) : RecyclerView.ViewHolder(layout.view) {

    fun onBind(item: T) = (layout as? Layout<T>)?.apply {
        if (isRunningTest() && isCallingFromMatcher())
            layout.view.onItAndChildrenTree { fakeAttachToWindow() }
        this@apply.item.set(item)
    }
}