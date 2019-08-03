package thevoid.iam.rx.adapter

class ViewHolder<T>(item : T, private val layout: Layout<T>) {

    init { layout.item.set(item) }

    fun <T> onBind(item: T) = (layout as? Layout<T>)?.item?.set(item)
}