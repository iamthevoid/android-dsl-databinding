package iam.thevoid.noxml.binding

import android.os.Build
import android.view.View
import iam.thevoid.e.safe
import java.lang.reflect.ParameterizedType
import java.lang.reflect.TypeVariable

class ObserveListener : View.OnAttachStateChangeListener {

    companion object {
        private const val DEPTH = 5
    }

    private var attached: Boolean = false

    private val observableSetters = mutableMapOf<String, Setter<out View, out Any>>()

    fun subscribeSetter(setter: Setter<out View, out Any>, tag: String = key()) {
        val alreadyObservable = observableSetters[tag]
        if (alreadyObservable?.theSameAs(setter).safe())
            alreadyObservable?.unsubscribeChanges()

        observableSetters[tag] = setter

        attached = attached || setter.view?.isAttached().safe()

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

    fun bindDataImmediate(v: View?) {
        observableSetters.values.forEach { it.applyChanges() }
    }

    private fun key(): String = with(Thread.currentThread().stackTrace) {

        buildString {
            for (i in size - 1 downTo DEPTH + 1)
                append(this@with[i].methodName).append("_")

            append(
                if (size < DEPTH + 1) "" else with(this@with[DEPTH]) {
                    Class.forName(className).methods.find { it.name == methodName }
                }
                ?.let {
                    if (it.genericParameterTypes.size > 1) {
                        "${it.name}_${(it.genericParameterTypes.component2() as? ParameterizedType)?.actualTypeArguments?.let { types ->
                            if (types.isNotEmpty()) types.component1().let { type ->
                                (type as? TypeVariable<*>)?.bounds.let { types ->
                                    (types?.firstOrNull() as? Class<*>)?.name
                                } ?: type.toString()
                            } else ""
                        }}"
                    } else
                        "${it.name}_${it.typeParameters.let { typeParameters ->
                            typeParameters.firstOrNull()?.bounds.let { types ->
                                (types?.firstOrNull() as? Class<*>)?.name
                            }.safe()
                        }}"
                }.safe()
            )
        }
    }

    private fun View.isAttached(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            isAttachedToWindow
        } else {
            windowToken != null
        }
}