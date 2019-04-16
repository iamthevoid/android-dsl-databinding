package thevoid.iam.components

import android.view.View
import io.reactivex.Flowable

fun <T : Any, V : View> V.addSetter(flowable : Flowable<T>,  setter:V.(T) -> Unit) {
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