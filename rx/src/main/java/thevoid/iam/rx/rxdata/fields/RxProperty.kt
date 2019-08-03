package thevoid.iam.rx.rxdata.fields

open class RxProperty {

    var rxCallback : RxPropertyChangedCallback? = null

    fun notifyChange() {
        rxCallback?.onItemChanged()
    }

}