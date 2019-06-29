package iam.thevoid.recycler

import androidx.recyclerview.widget.RecyclerView
import thevoid.iam.components.mvvm.adapter.Layout

class Holder<T>(private val layout: Layout<T>) : RecyclerView.ViewHolder(layout.view) {

    fun onBind(item: T) = (layout as? Layout<T>)?.item?.set(item)
}