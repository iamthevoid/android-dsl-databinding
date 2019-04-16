package thevoid.iam.components

import android.view.View

class ObserveListener : View.OnAttachStateChangeListener {

    private var attached: Boolean = false

    private val observableSetters = mutableMapOf<String, Setter<out View, out Any>>()

    fun subscribeSetter(tag : String, setter: Setter<out View, out Any>) {
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