@file:Suppress("unused")

package iam.thevoid.noxml.rx2.extensions.view

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.*
import iam.thevoid.ae.*
import iam.thevoid.e.mergeWith
import iam.thevoid.noxml.change.Margins
import iam.thevoid.noxml.change.scroll.OnFling
import iam.thevoid.noxml.change.scroll.OnScroll
import iam.thevoid.noxml.extensions.view.gestureDetector
import iam.thevoid.noxml.extensions.view.gestureDetectorCallback
import iam.thevoid.noxml.extensions.view.observeListener
import iam.thevoid.noxml.extensions.view.onGlobalLayoutDelegate
import iam.thevoid.noxml.rx2.data.RxLoading
import iam.thevoid.noxml.rx2.data.fields.*
import iam.thevoid.noxml.rx2.utils.RxSetter
import iam.thevoid.noxml.util.containsInStackTrace
import iam.thevoid.util.Optional
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor

fun <T : Any, V : View> V.addSetter(flowable: Flowable<T>, setter: V.(T) -> Unit = {}) {
    observeListener.apply {
        subscribeSetter(object : RxSetter<V, T>(this@addSetter, flowable) {
            override fun set(view: V?, component: T) {
                view?.apply { setter(this, component) }
            }
        })
    }
}

/**
 * Enabled
 */

fun View.disableWhileLoading(loading: RxLoading) =
    addSetter(loading.observe()) { isEnabled = !it }

fun View.enableWhileLoading(loading: RxLoading) =
    addSetter(loading.observe()) { isEnabled = it }

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
        Flowable.combineLatest(left, top, right, bottom, { l, t, r, b -> Margins(l, t, r, b) })
    ) { margins ->
        (layoutParams as? ViewGroup.MarginLayoutParams)
            ?.setMargins(margins.left, margins.top, margins.right, margins.bottom)
    }

/**
 * Visibility
 */


fun View.hideUntilLoaded(loading: Flowable<Boolean>) =
    addSetter(loading) { hide(it) }

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun View.hideUntilLoaded(loading: RxBoolean) =
    hideUntilLoaded(loading.observe())

fun View.hideUntilLoaded(loading: RxLoading) =
    hideUntilLoaded(loading.observe())

fun View.hideUntilLoaded(loading: RxLoading, vararg loadings: RxLoading) =
    hideUntilLoaded(Flowable.merge(loading.observe().mergeWith(loadings.map { it.observe() })))

fun View.hideWhenLoaded(loading: Flowable<Boolean>) =
    addSetter(loading) { hide(!it) }

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun View.hideWhenLoaded(loading: RxBoolean) =
    hideWhenLoaded(loading.observe())

fun View.hideWhenLoaded(loading: RxLoading) =
    hideWhenLoaded(loading.observe())

fun View.hideWhenLoaded(loading: RxLoading, vararg loadings: RxLoading) =
    hideWhenLoaded(Flowable.merge(loading.observe().mergeWith(loadings.map { it.observe() })))

fun View.goneUntilLoaded(loading: Flowable<Boolean>) =
    addSetter(loading) { gone(it) }

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun View.goneUntilLoaded(loading: RxBoolean) =
    goneUntilLoaded(loading.observe())

fun View.goneUntilLoaded(loading: RxLoading) =
    goneUntilLoaded(loading.observe())

fun View.goneUntilLoaded(loading: RxLoading, vararg loadings: RxLoading) =
    goneUntilLoaded(Flowable.merge(loading.observe().mergeWith(loadings.map { it.observe() })))

fun View.goneWhenLoaded(loading: Flowable<Boolean>) =
    addSetter(loading) { gone(!it) }

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun View.goneWhenLoaded(loading: RxBoolean) =
    goneWhenLoaded(loading.observe())

fun View.goneWhenLoaded(loading: RxLoading) =
    goneWhenLoaded(loading.observe())

fun View.goneWhenLoaded(loading: RxLoading, vararg loadings: RxLoading) =
    goneWhenLoaded(Flowable.merge(loading.observe().mergeWith(loadings.map { it.observe() })))

fun View.gone(needGone: Flowable<Boolean>) =
    addSetter(needGone) { gone(it) }

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun View.gone(needGone: RxField<Boolean>) =
    gone(needGone.onlyPresent())

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun View.gone(needGone: RxItem<Boolean>) =
    gone(needGone.observe())

fun View.hide(needHide: Flowable<Boolean>) =
    addSetter(needHide) { hide(it) }

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun View.hide(needHide: RxField<Boolean>) =
    hide(needHide.onlyPresent())

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun View.hide(needHide: RxItem<Boolean>) =
    hide(needHide.observe())

/**
 * Color
 */

fun View.setBackgroundColor(color: Flowable<Int>) =
    addSetter(color) { setBackgroundColor(it) }

fun View.setBackgroundColorResource(color: Flowable<Int>) =
    addSetter(color) { setBackgroundColor(color(it)) }

@Suppress("DEPRECATION")
fun View.setBackgroundDrawable(background: Flowable<Drawable>) =
    addSetter(background) { setBackgroundDrawable(it) }

fun View.setBackgroundResource(background: Flowable<Int>) =
    addSetter(background) { setBackgroundResource(it) }

/**
 * Alpha
 */

fun View.setAlpha(alpha: RxField<Float>) =
    setAlpha(alpha.onlyPresent())

fun View.setAlpha(alpha: RxItem<Float>) =
    setAlpha(alpha.observe())

fun View.setAlpha(alpha: Flowable<Float>) =
    addSetter(alpha) {
        this.alpha = when (it) {
            in 0.0..1.0 -> it
            else -> throw IllegalArgumentException("Alpha must be in 0..1f range, current value is $it")
        }
    }

/**
 * On click listener
 */

fun View.setClickListener(onClick: Flowable<View.OnClickListener>) =
    addSetter(onClick) {
        setOnClickListener(null)
        setOnClickListener(it)
    }

/**
 * Focus
 */

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun View.onFocusChange(onFocusChange: RxItem<Boolean>) =
    onFocusChange(onFocusChange) { it }

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun View.onFocusChange(onFocusChange: RxField<Boolean>) =
    onFocusChange(onFocusChange) { it }

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun <T : Any> View.onFocusChange(onFocusChange: RxField<T>, mapper: (Boolean) -> T?) {
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        onFocusChange.set(mapper(hasFocus))
    }
}

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun <T : Any> View.onFocusChange(onFocusChange: RxItem<T>, mapper: (Boolean) -> T) {
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        onFocusChange.set(mapper(hasFocus))
    }
}

fun View.onFocusChange(onFocusChange: FlowableProcessor<Boolean>) =
    onFocusChange(onFocusChange, mapper = { it })

fun <T : Any> View.onFocusChange(onFocusChange: FlowableProcessor<T>, mapper: (Boolean) -> T) {
    addSetter(Flowable.create<T>({
        onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            it.onNext(mapper(hasFocus))
        }
        it.setCancellable { onFocusChangeListener = null }
    }, BackpressureStrategy.LATEST).doOnNext(onFocusChange::onNext))
}

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun View.onFocusChangeForceFalseOnClearFocus(onFocusChange: RxBoolean) =
    onFocusChangeForceFalseOnClearFocus(onFocusChange) { it }

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun View.onFocusChangeForceFalseOnClearFocus(onFocusChange: RxItem<Boolean>) =
    onFocusChangeForceFalseOnClearFocus(onFocusChange) { it }

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun <T : Any> View.onFocusChangeForceFalseOnClearFocus(
    onFocusChange: RxItem<T>,
    mapper: (Boolean) -> T
) {
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        onFocusChange.set(
            mapper(
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P && hasFocus) {
                    if (containsInStackTrace("clearFocus")) false else hasFocus
                } else hasFocus
            )
        )
    }
}

fun <T : Any> View.onFocusChangeForceFalseOnClearFocus(
    onFocusChange: FlowableProcessor<T>,
    mapper: (Boolean) -> T
) {
    addSetter(Flowable.create<T>({
        onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            it.onNext(
                mapper(
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P && hasFocus) {
                        if (containsInStackTrace("clearFocus")) false else hasFocus
                    } else hasFocus
                )
            )
        }
        it.setCancellable { onFocusChangeListener = null }
    }, BackpressureStrategy.LATEST).doOnNext(onFocusChange::onNext))
}

/**
 * Transition
 */

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun View.setTranslationY(translation: RxField<Float>) =
    setTranslationY(translation.onlyPresent())

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun View.setTranslationY(translation: RxItem<Float>) =
    setTranslationY(translation.observe())

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun View.setTranslationY(translation: RxFloat) =
    setTranslationY(translation.observe())

fun View.setTranslationY(translation: Flowable<Float>) =
    addSetter(translation) { translationY = it }

/**
 * Height
 */

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
@Suppress("ObjectLiteralToLambda", "DEPRECATION")
fun View.getHeight(height: RxInt) {
    onGlobalLayoutDelegate.addOnGlobalLayoutCallback(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            height.set(measuredHeight)
        }
    })
}

fun View.getHeight(height: FlowableProcessor<Int>) {
    addSetter(Flowable.create<Int>({
        val callback = ViewTreeObserver.OnGlobalLayoutListener { it.onNext(measuredHeight) }
        onGlobalLayoutDelegate.addOnGlobalLayoutCallback(callback)
        it.setCancellable { onGlobalLayoutDelegate.removeOnGlobalLayoutCallback(callback) }
    }, BackpressureStrategy.LATEST).doOnNext(height::onNext))
}

/**
 * ON SCROLL
 */

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun <T : Any> View.onScroll(onScroll: RxField<T>, mapper: (OnScroll) -> T) {
    gestureDetectorCallback.addOnScrollCallback(object :
        GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            onScroll.set(mapper(OnScroll(e1, e2, distanceX, distanceY)))
            return true
        }
    })
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun View.onScroll(onScroll: RxField<OnScroll>) =
    onScroll(onScroll) { it }

fun <T : Any> View.onScroll(onScroll: FlowableProcessor<T>, mapper: (OnScroll) -> T) {
    addSetter(Flowable.create<T>({
        val callback = object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                it.onNext(mapper(OnScroll(e1, e2, distanceX, distanceY)))
                return true
            }
        }
        gestureDetectorCallback.addOnScrollCallback(callback)
        it.setCancellable { gestureDetectorCallback.removeOnScrollCallback(callback) }
    }, BackpressureStrategy.LATEST).doOnNext(onScroll::onNext))
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

fun View.onScroll(onScroll: FlowableProcessor<OnScroll>) =
    onScroll(onScroll) { it }

/**
 * ON FLING
 */

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun <T : Any> View.onFling(onFling: RxField<T>, mapper: (OnFling) -> T) {
    gestureDetectorCallback.addOnFlingCallback(object :
        GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            onFling.set(mapper(OnFling(e1, e2, velocityX, velocityY)))
            return true
        }
    })
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun View.onFling(onFling: RxField<OnFling>) =
    onFling(onFling) { it }

fun <T : Any> View.onFling(onFling: FlowableProcessor<T>, mapper: (OnFling) -> T) {
    addSetter(Flowable.create<T>({
        val callback = object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                it.onNext(mapper(OnFling(e1, e2, velocityX, velocityY)))
                return true
            }
        }
        gestureDetectorCallback.addOnFlingCallback(callback)
        it.setCancellable { gestureDetectorCallback.removeOnFlingCallback(callback) }
    }, BackpressureStrategy.LATEST).doOnNext(onFling::onNext))
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

fun View.onFling(onFling: FlowableProcessor<OnFling>) =
    onFling(onFling) { it }

/**
 * ON DOWN
 */

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun <T : Any> View.onDown(onDown: RxField<T>, mapper: (Optional<MotionEvent>) -> T) {
    gestureDetectorCallback.addOnDownCallback(object :
        GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            onDown.set(mapper(Optional.of(e)))
            return true
        }
    })
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun View.onDown(onDown: RxField<Optional<MotionEvent>>) =
    onDown(onDown) { it }

fun <T : Any> View.onDown(onDown: FlowableProcessor<T>, mapper: (Optional<MotionEvent>) -> T) {
    addSetter(Flowable.create<T>({
        val callback = object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent?): Boolean {
                it.onNext(mapper(Optional.of(e)))
                return true
            }
        }
        gestureDetectorCallback.addOnDownCallback(callback)
        it.setCancellable { gestureDetectorCallback.removeOnDownCallback(callback) }
    }, BackpressureStrategy.LATEST).doOnNext(onDown::onNext))
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

fun View.onDown(onDown: FlowableProcessor<Optional<MotionEvent>>) =
    onDown(onDown) { it }

/**
 * ON SINGLE TAP UP
 */

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun <T : Any> View.onSingleTapUp(
    onSingleTapUp: RxField<T>,
    mapper: (Optional<MotionEvent>) -> T
) {
    gestureDetectorCallback.addOnSingleTapUpCallback(object :
        GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            onSingleTapUp.set(mapper(Optional.of(e)))
            return true
        }
    })
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
}

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun View.onSingleTapUp(onSingleTapUp: RxField<Optional<MotionEvent>>) =
    onSingleTapUp(onSingleTapUp) { it }

fun <T : Any> View.onSingleTapUp(
    onSingleTapUp: FlowableProcessor<T>,
    mapper: (Optional<MotionEvent>) -> T
) {
    addSetter(Flowable.create<T>({
        val callback = object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                it.onNext(mapper(Optional.of(e)))
                return true
            }
        }
        gestureDetectorCallback.addOnSingleTapUpCallback(callback)
        it.setCancellable { gestureDetectorCallback.removeOnSingleTapUpCallback(callback) }
    }, BackpressureStrategy.LATEST).doOnNext(onSingleTapUp::onNext))
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

fun View.onSingleTapUp(onSingleTapUp: FlowableProcessor<Optional<MotionEvent>>) =
    onSingleTapUp(onSingleTapUp) { it }

/**
 * ON SHOW PRESS
 */

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun <T : Any> View.onShowPress(onShowPress: RxField<T>, mapper: (Optional<MotionEvent>) -> T) {
    gestureDetectorCallback.addOnShowPressCallback(object :
        GestureDetector.SimpleOnGestureListener() {
        override fun onShowPress(e: MotionEvent?) = onShowPress.set(mapper(Optional.of(e)))
    })
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun View.onShowPress(onShowPress: RxField<Optional<MotionEvent>>) =
    onShowPress(onShowPress) { it }

fun <T : Any> View.onShowPress(
    onShowPress: FlowableProcessor<T>,
    mapper: (Optional<MotionEvent>) -> T
) {
    addSetter(Flowable.create<T>({
        val callback = object : GestureDetector.SimpleOnGestureListener() {
            override fun onShowPress(e: MotionEvent?) = it.onNext(mapper(Optional.of(e)))
        }
        gestureDetectorCallback.addOnShowPressCallback(callback)
        it.setCancellable { gestureDetectorCallback.removeOnShowPressCallback(callback) }
    }, BackpressureStrategy.LATEST).doOnNext(onShowPress::onNext))
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

fun View.onShowPress(onShowPress: FlowableProcessor<Optional<MotionEvent>>) =
    onShowPress(onShowPress) { it }

/**
 * ON LONG PRESS
 */

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun <T : Any> View.onLongPress(onLongPress: RxField<T>, mapper: (Optional<MotionEvent>) -> T) {
    gestureDetectorCallback.addOnShowPressCallback(object :
        GestureDetector.SimpleOnGestureListener() {
        override fun onShowPress(e: MotionEvent?) = onLongPress.set(mapper(Optional.of(e)))
    })
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun View.onLongPress(onLongPress: RxField<Optional<MotionEvent>>) =
    onLongPress(onLongPress) { it }

fun <T : Any> View.onLongPress(onLongPress: FlowableProcessor<T>, mapper: (Optional<MotionEvent>) -> T) {
    addSetter(Flowable.create<T>({
        val callback = object : GestureDetector.SimpleOnGestureListener() {
            override fun onShowPress(e: MotionEvent?) = it.onNext(mapper(Optional.of(e)))
        }
        gestureDetectorCallback.addOnShowPressCallback(callback)
        it.setCancellable { gestureDetectorCallback.removeOnLongPressCallback(callback) }
    }, BackpressureStrategy.LATEST).doOnNext(onLongPress::onNext))
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

fun View.onLongPress(onLongPress: FlowableProcessor<Optional<MotionEvent>>) =
    onLongPress(onLongPress) { it }