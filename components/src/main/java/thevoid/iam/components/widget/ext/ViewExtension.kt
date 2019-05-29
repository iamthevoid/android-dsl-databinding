package thevoid.iam.components.widget.ext

import android.graphics.drawable.Drawable
import android.view.View
import iam.thevoid.ae.gone
import iam.thevoid.ae.hide
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.backgroundResource
import thevoid.iam.components.widget.util.ObserveListener
import thevoid.iam.components.R
import thevoid.iam.components.widget.Setter
import thevoid.iam.components.rx.RxLoading
import thevoid.iam.components.rx.fields.*

fun <T : Any, V : View> V.addSetter(flowable: Flowable<T>, setter: V.(T) -> Unit = {}) {
    observeListener.apply {
        subscribeSetter(object : Setter<V, T>(this@addSetter, flowable) {
            override fun set(view: V?, component: T) {
                view?.apply { setter(this, component) }
            }
        })
    }
}

fun <T : CharSequence, V : View> V.addGetter(consumer: ((T) -> Unit) -> Unit, rxCharSequence: RxCharSequence<T>) =
    addSetter(Flowable.create<T>({ emitter ->
        consumer { emitter.onNext(it) }
    }, BackpressureStrategy.LATEST)) { rxCharSequence.set(it) }

fun <T : Any, V : View> V.addGetter(consumer: ((T) -> Unit) -> Unit, rxField: RxField<T>) =
    addSetter(Flowable.create<T>({ emitter ->
        consumer { emitter.onNext(it) }
    }, BackpressureStrategy.LATEST)) { rxField.set(it) }

fun <V : View> V.addGetter(consumer: ((Int) -> Unit) -> Unit, rxInt: RxInt) =
    addSetter(Flowable.create<Int>({ emitter ->
        consumer { emitter.onNext(it) }
    }, BackpressureStrategy.LATEST)) { rxInt.set(it) }

fun <V : View> V.addGetter(consumer: ((Long) -> Unit) -> Unit, rxLong: RxLong) =
    addSetter(Flowable.create<Long>({ emitter ->
        consumer { emitter.onNext(it) }
    }, BackpressureStrategy.LATEST)) { rxLong.set(it) }

fun <V : View> V.addGetter(consumer: ((Boolean) -> Unit) -> Unit, rxBoolean: RxBoolean) =
    addSetter(Flowable.create<Boolean>({ emitter ->
        consumer { emitter.onNext(it) }
    }, BackpressureStrategy.LATEST)) { rxBoolean.set(it) }

fun <V : View> V.addGetter(consumer: ((Double) -> Unit) -> Unit, rxDouble: RxDouble) =
    addSetter(Flowable.create<Double>({ emitter ->
        consumer { emitter.onNext(it) }
    }, BackpressureStrategy.LATEST)) { rxDouble.set(it) }

fun <V : View> V.addGetter(consumer: ((Float) -> Unit) -> Unit, rxFloat: RxFloat) =
    addSetter(Flowable.create<Float>({ emitter ->
        consumer { emitter.onNext(it) }
    }, BackpressureStrategy.LATEST)) { rxFloat.set(it) }

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

fun View.setBackgroundColor(color: Flowable<Int>) =
    addSetter(color) { backgroundColor = it }

fun View.setBackgroundColorResource(color: Flowable<Int>) =
    addSetter(color) { backgroundColorResource = it }

fun View.setBackgroundDrawable(background: Flowable<Drawable>) =
    addSetter(background) { backgroundDrawable = it }

fun View.setBackgroundResource(background: Flowable<Int>) =
    addSetter(background) { backgroundResource = it }