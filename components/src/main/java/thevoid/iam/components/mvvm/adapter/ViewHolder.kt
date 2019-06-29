package thevoid.iam.components.mvvm.adapter

class ViewHolder(private val layout: Layout<*>) {

    val itemView
        get() = layout.view

    fun <T> onBind(item: T) = (layout as? Layout<T>)?.item?.set(item)
}