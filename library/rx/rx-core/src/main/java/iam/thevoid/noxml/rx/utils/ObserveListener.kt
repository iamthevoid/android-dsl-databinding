package iam.thevoid.noxml.rx.utils

import android.view.View
import iam.thevoid.e.safe

class ObserveListener : View.OnAttachStateChangeListener {

    private var attached: Boolean = false

    private val observableSetters = mutableMapOf<String, RxSetter<out View, out Any>>()

    fun subscribeSetter(setter: RxSetter<out View, out Any>, tag: String = key()) {
        val alreadyObservable = observableSetters[tag]
        if (alreadyObservable?.theSameAs(setter).safe())
            alreadyObservable?.unsubscribeChanges()

        observableSetters[tag] = setter

        if (attached)
            setter.subscribeChanges()
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