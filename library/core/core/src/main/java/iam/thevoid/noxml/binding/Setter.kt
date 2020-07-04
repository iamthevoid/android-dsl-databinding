package iam.thevoid.noxml.binding

import android.view.View
import java.lang.ref.WeakReference

abstract class Setter<V : View, C>(view: V) {

    protected val viewRef: WeakReference<V> = WeakReference(view)

    abstract fun set(view: V?, component: C)

    val view
        get() = viewRef.get()

    /**
     * Blocking awaiting of first element. Here use
     */
    abstract fun applyFirstChangeBlocking()

    abstract fun subscribeChanges()

    abstract fun unsubscribeChanges()

    abstract fun theSameAs(setter: Setter<*, *>): Boolean
}