@file:Suppress("unused")

package iam.thevoid.noxml.rx.extensions

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.*
import iam.thevoid.ae.*
import iam.thevoid.noxml.change.scroll.OnFling
import iam.thevoid.noxml.change.scroll.OnScroll
import iam.thevoid.e.mergeWith
import iam.thevoid.util.Optional
import io.reactivex.Flowable
import io.reactivex.functions.Function4
import iam.thevoid.noxml.rx.recycler.extensions.*
import iam.thevoid.noxml.change.Margins
import iam.thevoid.noxml.util.containsInStackTrace
import iam.thevoid.noxml.rx.data.RxLoading
import iam.thevoid.noxml.rx.data.fields.*
import iam.thevoid.noxml.rx.utils.RxSetter

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
        Flowable.combineLatest<Int, Int, Int, Int, Margins>(left, top, right, bottom,
            Function4 { l, t, r, b -> Margins(l, t, r, b) })
    ) { margins ->
        (layoutParams as? ViewGroup.MarginLayoutParams)
            ?.setMargins(margins.left, margins.top, margins.right, margins.bottom)
    }

/**
 * Visibility
 */


fun View.hideUntilLoaded(loading: Flowable<Boolean>) =
    addSetter(loading) { hide(it) }

fun View.hideUntilLoaded(loading: RxBoolean) =
    hideUntilLoaded(loading.observe())

fun View.hideUntilLoaded(loading: RxLoading) =
    hideUntilLoaded(loading.observe())

fun View.hideUntilLoaded(loading: RxLoading, vararg loadings: RxLoading) =
    hideUntilLoaded(Flowable.merge(loading.observe().mergeWith(loadings.map { it.observe() })))

fun View.hideWhenLoaded(loading: Flowable<Boolean>) =
    addSetter(loading) { hide(!it) }

fun View.hideWhenLoaded(loading: RxBoolean) =
    hideWhenLoaded(loading.observe())

fun View.hideWhenLoaded(loading: RxLoading) =
    hideWhenLoaded(loading.observe())

fun View.hideWhenLoaded(loading: RxLoading, vararg loadings: RxLoading) =
    hideWhenLoaded(Flowable.merge(loading.observe().mergeWith(loadings.map { it.observe() })))

fun View.goneUntilLoaded(loading: Flowable<Boolean>) =
    addSetter(loading) { gone(it) }

fun View.goneUntilLoaded(loading: RxBoolean) =
    goneUntilLoaded(loading.observe())

fun View.goneUntilLoaded(loading: RxLoading) =
    goneUntilLoaded(loading.observe())

fun View.goneUntilLoaded(loading: RxLoading, vararg loadings: RxLoading) =
    goneUntilLoaded(Flowable.merge(loading.observe().mergeWith(loadings.map { it.observe() })))

fun View.goneWhenLoaded(loading: Flowable<Boolean>) =
    addSetter(loading) { gone(!it) }

fun View.goneWhenLoaded(loading: RxBoolean) =
    goneWhenLoaded(loading.observe())

fun View.goneWhenLoaded(loading: RxLoading) =
    goneWhenLoaded(loading.observe())

fun View.goneWhenLoaded(loading: RxLoading, vararg loadings: RxLoading) =
    goneWhenLoaded(Flowable.merge(loading.observe().mergeWith(loadings.map { it.observe() })))

fun View.gone(needGone: Flowable<Boolean>) =
    addSetter(needGone) { gone(it) }

fun View.gone(needGone: RxField<Boolean>) =
    gone(needGone.onlyPresent())

fun View.gone(needGone: RxItem<Boolean>) =
    gone(needGone.observe())

fun View.hide(needHide: Flowable<Boolean>) =
    addSetter(needHide) { hide(it) }

fun View.hide(needHide: RxField<Boolean>) =
    hide(needHide.onlyPresent())

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

fun View.setOnclickListener(onClick: Flowable<View.OnClickListener>) =
    addSetter(onClick) {
        setOnClickListener(null)
        setOnClickListener(it)
    }

/**
 * Focus
 */

fun View.onFocusChange(onFocusChange: RxItem<Boolean>) =
    onFocusChange(onFocusChange) { it }

fun View.onFocusChange(onFocusChange: RxField<Boolean>) =
    onFocusChange(onFocusChange) { it }

fun <T : Any> View.onFocusChange(onFocusChange: RxField<T>, mapper: (Boolean) -> T?) {
    onFocusChange.set(mapper(isFocused))
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        onFocusChange.set(mapper(hasFocus))
    }
}

fun <T : Any> View.onFocusChange(onFocusChange: RxItem<T>, mapper: (Boolean) -> T) {
    onFocusChange.set(mapper(isFocused))
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        onFocusChange.set(mapper(hasFocus))
    }
}

fun View.onFocusChangeForceFalseOnClearFocus(onFocusChange: RxBoolean) =
    onFocusChangeForceFalseOnClearFocus(onFocusChange) { it }

fun View.onFocusChangeForceFalseOnClearFocus(onFocusChange: RxItem<Boolean>) =
    onFocusChangeForceFalseOnClearFocus(onFocusChange) { it }

fun <T : Any> View.onFocusChangeForceFalseOnClearFocus(
    onFocusChange: RxItem<T>,
    mapper: (Boolean) -> T
) {
    onFocusChange.set(mapper(isFocused))
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

/**
 * Transition
 */

fun View.setTranslationY(translation: RxField<Float>) =
    setTranslationY(translation.onlyPresent())

fun View.setTranslationY(translation: RxItem<Float>) =
    setTranslationY(translation.observe())

fun View.setTranslationY(translation: RxFloat) =
    setTranslationY(translation.observe())

fun View.setTranslationY(translation: Flowable<Float>) =
    addSetter(translation) { translationY = it }

/**
 * Height
 */

@Suppress("ObjectLiteralToLambda", "DEPRECATION")
fun View.getHeight(height: RxInt) {
    onGlobalLayoutDelegate.addOnGlobalLayoutCallback(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            height.set(measuredHeight)
        }
    })
}

/**
 * ON SCROLL
 */

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

fun View.onScroll(onScroll: RxField<OnScroll>) =
    onScroll(onScroll) { it }

/**
 * ON FLING
 */

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

fun View.onFling(onFling: RxField<OnFling>) =
    onFling(onFling) { it }

/**
 * ON DOWN
 */

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

fun View.onDown(onDown: RxField<Optional<MotionEvent>>) =
    onDown(onDown) { it }

/**
 * ON SINGLE TAP UP
 */

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

fun View.onSingleTapUp(onSingleTapUp: RxField<Optional<MotionEvent>>) =
    onSingleTapUp(onSingleTapUp) { it }

/**
 * ON SHOW PRESS
 */

fun <T : Any> View.onShowPress(onShowPress: RxField<T>, mapper: (Optional<MotionEvent>) -> T) {
    gestureDetectorCallback.addOnShowPressCallback(object :
        GestureDetector.SimpleOnGestureListener() {
        override fun onShowPress(e: MotionEvent?) = onShowPress.set(mapper(Optional.of(e)))
    })
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

fun View.onShowPress(onShowPress: RxField<Optional<MotionEvent>>) =
    onShowPress(onShowPress) { it }

/**
 * ON LONG PRESS
 */

fun <T : Any> View.onLongPress(onLongPress: RxField<T>, mapper: (Optional<MotionEvent>) -> T) {
    gestureDetectorCallback.addOnShowPressCallback(object :
        GestureDetector.SimpleOnGestureListener() {
        override fun onShowPress(e: MotionEvent?) = onLongPress.set(mapper(Optional.of(e)))
    })
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

fun View.onLongPress(onLongPress: RxField<Optional<MotionEvent>>) =
    onLongPress(onLongPress) { it }