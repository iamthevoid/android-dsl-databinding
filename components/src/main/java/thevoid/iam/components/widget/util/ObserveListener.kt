package thevoid.iam.components.widget.util

import android.view.View
import thevoid.iam.components.widget.Setter

class ObserveListener : View.OnAttachStateChangeListener {

    private var attached: Boolean = false

    private val observableSetters = mutableMapOf<String, Setter<out View, out Any>>()

    fun subscribeSetter(setter: Setter<out View, out Any>, tag : String = key()) {
        val alreadyObservable = observableSetters[tag]
        if (alreadyObservable?.theSameAs(setter) != true) {
            observableSetters[tag] = setter
            if (attached)
                setter.subscribeChanges()
        }
    }

    override fun onViewDetachedFromWindow(v: View?) {
        attached = false
        observableSetters.values.forEach { it.unsubscribeChanges() }
    }

    override fun onViewAttachedToWindow(v: View?) {
        attached = true
        observableSetters.values.forEach { it.subscribeChanges() }
    }
}