package iam.thevoid.recycler

import androidx.recyclerview.widget.RecyclerView

class Holder(private val layout: Layout<*>) : RecyclerView.ViewHolder(layout.view) {
    fun  <T> onBind(item: T) = (layout as? Layout<T>)?.item?.set(item)
}