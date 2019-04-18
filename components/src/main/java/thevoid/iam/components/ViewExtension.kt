package thevoid.iam.components

import android.view.View
import iam.thevoid.ae.hide
import io.reactivex.Flowable
import thevoid.iam.components.rx.RxFlowableLoading
import thevoid.iam.components.rx.RxLoading

fun <T : Any, V : View> V.addSetter(flowable: Flowable<T>, setter: V.(T) -> Unit) {
    observeListener.apply {
        subscribeSetter(key(), object : Setter<V, T>(this@addSetter, flowable) {
            override fun set(view: V?, component: T) {
                view?.apply { setter(this, component) }
            }
        })
    }
}

private val View.observeListener: ObserveListener
    get() = ((getTag(R.id.listener) as? ObserveListener)
        ?: ObserveListener().also {
            setTag(R.id.listener, it)
            addOnAttachStateChangeListener(it)
        })

fun View.hideUntilLoaded(loading: RxLoading) =
    addSetter(loading.flowable) { hide(it) }

fun View.showUntilLoaded(loading: RxLoading) =
    addSetter(loading.flowable) { hide(!it) }