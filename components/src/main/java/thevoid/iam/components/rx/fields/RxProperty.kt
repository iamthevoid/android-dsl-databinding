package thevoid.iam.components.rx.fields

open class RxProperty {

    var rxCallback : RxPropertyChangedCallback? = null

    fun notifyChange() {
        rxCallback?.onItemChanged()
    }

}