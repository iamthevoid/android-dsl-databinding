package thevoid.iam.rx.widget.ext

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import iam.thevoid.ae.*
import iam.thevoid.common.adapter.change.scroll.OnFling
import iam.thevoid.common.adapter.change.scroll.OnScroll
import iam.thevoid.common.adapter.delegate.OnGestureDelegate
import iam.thevoid.e.mergeWith
import iam.thevoid.e.safe
import iam.thevoid.util.Optional
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.functions.Function4
import thevoid.iam.rx.R
import thevoid.iam.rx.rxdata.RxLoading
import thevoid.iam.rx.rxdata.fields.*
import thevoid.iam.rx.widget.Setter
import thevoid.iam.rx.widget.util.ObserveListener
import thevoid.iam.rx.widget.util.containsInStackTrace

/**
 * =============== BASE ==============
 */

/**
 * Basics.
 * @observeListener is the child of @see#View.OnAttachStateChangeListener and the core of reactive UI.
 *
 * Stores all flowables and subscribe on it changes on attach to window and unsubscribe on detach from window.
 */
private val View.observeListener: ObserveListener
    get() = ((getTag(R.id.listener) as? ObserveListener)
        ?: ObserveListener().also {
            setTag(R.id.listener, it)
            addOnAttachStateChangeListener(it)
        })

fun <T : Any, V : View> V.addSetter(flowable: Flowable<T>, setter: V.(T) -> Unit = {}) {
    observeListener.apply {
        subscribeSetter(object : Setter<V, T>(this@addSetter, flowable) {
            override fun set(view: V?, component: T) {
                view?.apply { setter(this, component) }
            }
        })
    }
}

fun <T : CharSequence, V : View> V.addGetter(
    consumer: ((T) -> Unit) -> Unit,
    rxCharSequence: RxCharSequence<T>
) =
    addSetter(Flowable.create<T>({ emitter ->
        consumer { emitter.onNext(it) }
    }, BackpressureStrategy.LATEST)) { rxCharSequence.set(it) }

fun <T : Any, V : View> V.addGetter(consumer: ((T?) -> Unit) -> Unit, rxField: RxField<T>) =
    addSetter(Flowable.create<Optional<T>>({ emitter ->
        consumer { emitter.onNext(Optional.of(it)) }
    }, BackpressureStrategy.LATEST)) { rxField.set(it.item) }

fun <T : Any, V : View> V.addGetter(consumer: ((T) -> Unit) -> Unit, rxField: RxItem<T>) =
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

/**
 * Enabled
 */

fun View.disableWhileLoading(loading: RxLoading) =
    addSetter(loading.asFlowable) { isEnabled = !it }

fun View.enableWhileLoading(loading: RxLoading) =
    addSetter(loading.asFlowable) { isEnabled = it }

fun View.setEnabled(enabled: Flowable<Boolean>) =
    addSetter(enabled) { isEnabled = it }

/**
 * Margin
 */

fun View.setLeftMargin(leftMargin: Flowable<Int>) = setMargins(left = leftMargin)

fun View.setTopMargin(topMargin: Flowable<Int>) = setMargins(top = topMargin)

fun View.setRightMargin(rightMargin: Flowable<Int>) = setMargins(right = rightMargin)

fun View.setBottomMargin(bottomMargin: Flowable<Int>) = setMargins(bottom = bottomMargin)

fun View.setMargins(
    left: Flowable<Int> = Flowable.just(marginLeft),
    top: Flowable<Int> = Flowable.just(marginTop),
    right: Flowable<Int> = Flowable.just(marginRight),
    bottom: Flowable<Int> = Flowable.just(marginBottom)
) =
    addSetter(
        Flowable.combineLatest<Int, Int, Int, Int, Margins>(left, top, right, bottom,
            Function4 { l, t, r, b -> Margins(l, t, r, b) })
    ) { margins ->
        (layoutParams as? ViewGroup.MarginLayoutParams)
            ?.setMargins(margins.left, margins.top, margins.right, margins.bottom)
    }

/**
 * Visibility
 */


fun View.hideUntilLoaded(loading: RxLoading) =
    addSetter(loading.asFlowable) { hide(it) }

fun View.hideUntilLoaded(loading: RxLoading, vararg loadings: RxLoading) =
    addSetter(Flowable.merge(loading.asFlowable.mergeWith(loadings.map { it.asFlowable }))) {
        hide(
            it
        )
    }

fun View.hideWhenLoaded(loading: RxLoading) =
    addSetter(loading.asFlowable) { hide(!it) }

fun View.hideWhenLoaded(loading: RxLoading, vararg loadings: RxLoading) =
    addSetter(Flowable.merge(loading.asFlowable.mergeWith(loadings.map { it.asFlowable }))) { hide(!it) }

fun View.goneUntilLoaded(loading: RxLoading) =
    addSetter(loading.asFlowable) { gone(it) }

fun View.goneUntilLoaded(loading: RxLoading, vararg loadings: RxLoading) =
    addSetter(Flowable.merge(loading.asFlowable.mergeWith(loadings.map { it.asFlowable }))) {
        gone(
            it
        )
    }

fun View.goneWhenLoaded(loading: RxLoading) =
    addSetter(loading.asFlowable) { gone(!it) }

fun View.goneWhenLoaded(loading: RxLoading, vararg loadings: RxLoading) =
    addSetter(Flowable.merge(loading.asFlowable.mergeWith(loadings.map { it.asFlowable }))) { gone(!it) }

fun View.gone(needGone: Flowable<Boolean>) =
    addSetter(needGone) { gone(it) }

fun View.gone(needGone: RxField<Boolean>) =
    addSetter(needGone.observe()) { gone(it.item.safe()) }

fun View.gone(needGone: RxItem<Boolean>) =
    addSetter(needGone.observe()) { gone(it) }

fun View.hide(needHide: Flowable<Boolean>) =
    addSetter(needHide) { hide(it) }

fun View.hide(needHide: RxField<Boolean>) =
    addSetter(needHide.observe()) { hide(it.item.safe()) }

fun View.hide(needHide: RxItem<Boolean>) =
    addSetter(needHide.observe()) { hide(it) }

fun View.setBackgroundColor(color: Flowable<Int>) =
    addSetter(color) { setBackgroundColor(it) }

fun View.setBackgroundColorResource(color: Flowable<Int>) =
    addSetter(color) { setBackgroundColor(color(it)) }

fun View.setBackgroundDrawable(background: Flowable<Drawable>) =
    addSetter(background) { setBackgroundDrawable(it) }

fun View.setBackgroundResource(background: Flowable<Int>) =
    addSetter(background) { setBackgroundResource(it) }

/**
 * Alpha
 */

fun View.setAlpha(alpha: RxItem<Float>) =
    setAlpha(alpha.observe())

fun View.setAlpha(alphaFlowable: Flowable<Float>) =
    addSetter(alphaFlowable) {
        alpha = when (it) {
            in 0.0..1.0 -> it
            else -> throw IllegalArgumentException("Alpha must be in 0..1f range, current value is $it")
        }
    }

/**
 * On click listener
 */

fun View.setOnclickListener(onClick: Flowable<View.OnClickListener>) =
    addSetter(onClick) {
        setOnClickListener(null)
        setOnClickListener(it)
    }

/**
 * Focus
 */

fun View.onFocusChange(onChange: RxItem<Boolean>) =
    onFocusChange(onChange) { it }

fun View.onFocusChange(onChange: RxField<Boolean>) =
    onFocusChange(onChange) { it }

fun <T : Any> View.onFocusChange(onChange: RxField<T>, mapper: (Boolean) -> T?) =
    addGetter({ bypass ->
        bypass.invoke(mapper(isFocused))
        onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            bypass.invoke(mapper(hasFocus))
        }
    }, onChange)

fun <T : Any> View.onFocusChange(onChange: RxItem<T>, mapper: (Boolean) -> T) =
    addGetter({ bypass ->
        bypass.invoke(mapper(isFocused))
        onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            bypass.invoke(mapper(hasFocus))
        }
    }, onChange)

fun View.onFocusChangeForceFalseOnClearFocus(onChange: RxBoolean) =
    onFocusChangeForceFalseOnClearFocus(onChange) { it }

fun View.onFocusChangeForceFalseOnClearFocus(onChange: RxItem<Boolean>) =
    onFocusChangeForceFalseOnClearFocus(onChange) { it }

fun <T : Any> View.onFocusChangeForceFalseOnClearFocus(
    onChange: RxItem<T>,
    mapper: (Boolean) -> T
) =
    addGetter({ bypass ->
        bypass.invoke(mapper(isFocused))
        onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            bypass.invoke(
                mapper(
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P && hasFocus) {
                        if (containsInStackTrace("clearFocus")) false else hasFocus
                    } else hasFocus
                )
            )
        }
    }, onChange)


/**
 * Transition
 */

fun View.setTranslationY(rxTranslation: RxFloat) =
    setTranslationY(rxTranslation.observe())

fun View.setTranslationY(rxTranslation: Flowable<Float>) =
    addSetter(rxTranslation) { translationY = it }

/**
 * Gesture
 */

private val View.gestureDetector
    get() = (getTag(R.id.gestureDetector) as? GestureDetector) ?: GestureDetector(
        context,
        gestureDetectorCallback
    )

private val View.gestureDetectorCallback: OnGestureDelegate
    get() = (getTag(R.id.gestureDetectorCallback) as? OnGestureDelegate)
        ?: OnGestureDelegate().also { setTag(R.id.gestureDetectorCallback, it) }

/**
 * ON SCROLL
 */

fun View.onScroll(rxOnScroll: RxField<OnScroll>) = onScroll(rxOnScroll) { it }

fun <T : Any> View.onScroll(rxOnScroll: RxField<T>, mapper: (OnScroll) -> T) =
    addGetter({
        gestureDetectorCallback.addOnScrollCallback(object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                it.invoke(mapper(OnScroll(e1, e2, distanceX, distanceY)))
                return true
            }
        })
        setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }, rxOnScroll).also { setClickable() }


/**
 * ON FLING
 */


fun View.onFling(rxOnFling: RxField<OnFling>) = onFling(rxOnFling) { it }

fun <T : Any> View.onFling(rxOnFling: RxField<T>, mapper: (OnFling) -> T) =
    addGetter({
        gestureDetectorCallback.addOnFlingCallback(object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                it.invoke(mapper(OnFling(e1, e2, velocityX, velocityY)))
                return true
            }
        })
        setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }, rxOnFling).also { setClickable() }


/**
 * ON DOWN
 */


fun View.onDown(rxOnDown: RxField<Optional<MotionEvent>>) = onDown(rxOnDown) { it }

fun <T : Any> View.onDown(rxOnDown: RxField<T>, mapper: (Optional<MotionEvent>) -> T) =
    addGetter({
        gestureDetectorCallback.addOnDownCallback(object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent?): Boolean {
                it.invoke(mapper(Optional.of(e)))
                return true
            }
        })
        setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }, rxOnDown).also { setClickable() }

/**
 * ON SINGLE TAP UP
 */

fun View.onSingleTapUp(rxOnSingleTapUp: RxField<Optional<MotionEvent>>) =
    onSingleTapUp(rxOnSingleTapUp) { it }

fun <T : Any> View.onSingleTapUp(
    rxOnSingleTapUp: RxField<T>,
    mapper: (Optional<MotionEvent>) -> T
) =
    addGetter({
        gestureDetectorCallback.addOnSingleTapUpCallback(object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                it.invoke(mapper(Optional.of(e)))
                return true
            }
        })
        setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }, rxOnSingleTapUp).also { setClickable() }

/**
 * ON SHOW PRESS
 */

fun View.onShowPress(rxOnShowPress: RxField<Optional<MotionEvent>>) =
    onShowPress(rxOnShowPress) { it }

fun <T : Any> View.onShowPress(rxOnShowPress: RxField<T>, mapper: (Optional<MotionEvent>) -> T) =
    addGetter({
        gestureDetectorCallback.addOnShowPressCallback(object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onShowPress(e: MotionEvent?) = it.invoke(mapper(Optional.of(e)))
        })
        setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }, rxOnShowPress).also { setClickable() }

/**
 * ON LONG PRESS
 */

fun View.onLongPress(rxOnLongPress: RxField<Optional<MotionEvent>>) =
    onLongPress(rxOnLongPress) { it }

fun <T : Any> View.onLongPress(rxOnLongPress: RxField<T>, mapper: (Optional<MotionEvent>) -> T) =
    addGetter({
        gestureDetectorCallback.addOnLongPressCallback(object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent?) = it.invoke(mapper(Optional.of(e)))
        })
        setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }, rxOnLongPress).also { setClickable() }

data class Margins(val left: Int, val top: Int, val right: Int, val bottom: Int)