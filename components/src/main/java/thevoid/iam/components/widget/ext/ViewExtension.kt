package thevoid.iam.components.widget.ext

import android.view.View
import iam.thevoid.ae.gone
import iam.thevoid.ae.hide
import io.reactivex.Flowable
import thevoid.iam.components.widget.util.ObserveListener
import thevoid.iam.components.R
import thevoid.iam.components.widget.Setter
import thevoid.iam.components.widget.util.key
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

fun View.hideWhenLoaded(loading: RxLoading) =
    addSetter(loading.flowable) { hide(!it) }

fun View.goneUntilLoaded(loading: RxLoading) =
    addSetter(loading.flowable) { gone(it) }

fun View.goneWhenLoaded(loading: RxLoading) =
    addSetter(loading.flowable) { gone(!it) }

fun View.gone(needGone: Flowable<Boolean>) =
    addSetter(needGone) { gone(it) }

fun View.hide(needHide: Flowable<Boolean>) =
    addSetter(needHide) { hide(it) }