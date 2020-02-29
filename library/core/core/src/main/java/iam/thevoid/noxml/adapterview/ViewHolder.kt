package iam.thevoid.noxml.adapterview

class ViewHolder<T>(item : T, private val layout: Layout<T>) {

    init { layout.set(item) }

    @Suppress("UNCHECKED_CAST")
    fun <T> onBind(item: T) = (layout as? Layout<T>)?.set(item)
}