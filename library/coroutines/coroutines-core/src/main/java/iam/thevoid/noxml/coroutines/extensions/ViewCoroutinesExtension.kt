@file:UseExperimental(ExperimentalCoroutinesApi::class)
@file:Suppress("unused")

package iam.thevoid.noxml.coroutines.extensions

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.*
import iam.thevoid.ae.*
import iam.thevoid.noxml.coroutines.utils.CoroutinesSetter
import iam.thevoid.noxml.coroutines.data.*
import iam.thevoid.noxml.rx.recycler.extensions.gestureDetector
import iam.thevoid.noxml.rx.recycler.extensions.gestureDetectorCallback
import iam.thevoid.noxml.rx.recycler.extensions.observeListener
import iam.thevoid.noxml.change.Margins
import iam.thevoid.noxml.change.scroll.OnFling
import iam.thevoid.noxml.change.scroll.OnScroll
import iam.thevoid.noxml.util.containsInStackTrace
import iam.thevoid.noxml.rx.recycler.extensions.onGlobalLayoutDelegate
import iam.thevoid.util.Optional
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun <T : Any, V : View> V.addSetter(
    flow: Flow<T>,
    setter: V.(T) -> Unit = {}
) {
    observeListener.apply {
        subscribeSetter(object : CoroutinesSetter<V, T>(this@addSetter, flow) {
            override fun set(view: V?, component: T) {
                view?.apply { setter(this, component) }
            }
        })
    }
}

/**
 * Enabled
 */

fun View.disableWhileLoading(loading: CoroutineBoolean) =
    addSetter(loading.observe()) { isEnabled = !it }

fun View.enableWhileLoading(loading: CoroutineBoolean) =
    addSetter(loading.observe()) { isEnabled = it }

fun View.setEnabled(enabled: Flow<Boolean>) =
    addSetter(enabled) { isEnabled = it }

/**
 * Margin
 */

fun View.setLeftMargin(leftMargin: Flow<Int>) = setMargins(left = leftMargin)

fun View.setTopMargin(topMargin: Flow<Int>) = setMargins(top = topMargin)

fun View.setRightMargin(rightMargin: Flow<Int>) = setMargins(right = rightMargin)

fun View.setBottomMargin(bottomMargin: Flow<Int>) = setMargins(bottom = bottomMargin)

fun View.setMargins(
    left: Flow<Int> = flowOf(marginLeft),
    top: Flow<Int> = flowOf(marginTop),
    right: Flow<Int> = flowOf(marginRight),
    bottom: Flow<Int> = flowOf(marginBottom)
) =
    addSetter(combine(left, top, right, bottom) { (l, t, r, b) ->
        Margins(l, t, r, b)
    }) { margins ->
        (layoutParams as? ViewGroup.MarginLayoutParams)
            ?.setMargins(margins.left, margins.top, margins.right, margins.bottom)
    }

/**
 * Visibility
 */


fun View.hideUntilLoaded(loading: CoroutineBoolean) =
    addSetter(loading.observe()) { hide(it) }

fun View.hideUntilLoaded(loading: CoroutineBoolean, vararg loadings: CoroutineBoolean) =
    addSetter(merge(*mutableListOf(loading).apply { addAll(loadings) }.map { it.observe() }.toTypedArray())) {
        hide(it)
    }

fun View.hideWhenLoaded(loading: CoroutineBoolean) =
    addSetter(loading.observe()) { hide(!it) }

fun View.hideWhenLoaded(loading: CoroutineBoolean, vararg loadings: CoroutineBoolean) =
    addSetter(merge(*mutableListOf(loading).apply { addAll(loadings) }.map { it.observe() }.toTypedArray())) {
        hide(!it)
    }

fun View.goneUntilLoaded(loading: CoroutineBoolean) =
    addSetter(loading.observe()) { gone(it) }

fun View.goneWhenLoaded(loading: CoroutineBoolean) =
    addSetter(loading.observe()) { gone(!it) }

fun View.gone(needGone: Flow<Boolean>) =
    addSetter(needGone) { gone(it) }

fun View.gone(needGone: CoroutineField<Boolean>) =
    addSetter(needGone.onlyPresent()) { gone(it) }

fun View.gone(needGone: CoroutineItem<Boolean>) =
    addSetter(needGone.observe()) { gone(it) }

fun View.gone(needGone: CoroutineBoolean) =
    addSetter(needGone.observe()) { gone(it) }

fun View.hide(needHide: Flow<Boolean>) =
    addSetter(needHide) { hide(it) }

fun View.hide(needHide: CoroutineField<Boolean>) =
    addSetter(needHide.onlyPresent()) { hide(it) }

fun View.hide(needHide: CoroutineItem<Boolean>) =
    addSetter(needHide.observe()) { hide(it) }

fun View.hide(needHide: CoroutineBoolean) =
    addSetter(needHide.observe()) { hide(it) }

fun View.setBackgroundColor(color: Flow<Int>) =
    addSetter(color) { setBackgroundColor(it) }

fun View.setBackgroundColorResource(colorResource: Flow<Int>) =
    addSetter(colorResource) { setBackgroundColor(color(it)) }

@Suppress("DEPRECATION")
fun View.setBackgroundDrawable(backgroundDrawable: Flow<Drawable>) =
    addSetter(backgroundDrawable) { setBackgroundDrawable(it) }

fun View.setBackgroundResource(backgroundResource: Flow<Int>) =
    addSetter(backgroundResource) { setBackgroundResource(it) }

/**
 * Alpha
 */

fun View.setAlpha(alpha: CoroutineItem<Float>) =
    setAlpha(alpha.observe())

fun View.setAlpha(alpha: CoroutineField<Float>) =
    setAlpha(alpha.onlyPresent())

fun View.setAlpha(alpha: CoroutineFloat) =
    setAlpha(alpha.observe())

fun View.setAlpha(alpha: Flow<Float>) =
    addSetter(alpha) {
        this.alpha = when (it) {
            in 0.0..1.0 -> it
            else -> throw IllegalArgumentException("Alpha must be in 0..1f range, current value is $it")
        }
    }

/**
 * On click listener
 */

fun View.setOnclickListener(onClick: Flow<View.OnClickListener>) =
    addSetter(onClick) {
        setOnClickListener(null)
        setOnClickListener(it)
    }

/**
 * Focus
 */


fun View.onFocusChange(onFocusChange: CoroutineField<Boolean>) =
    onFocusChange(onFocusChange) { it }

fun View.onFocusChange(onFocusChange: CoroutineBoolean) =
    onFocusChange(onFocusChange) { it }

fun <T : Any> View.onFocusChange(onFocusChange: CoroutineField<T>, mapper: (Boolean) -> T) {
    onFocusChange.set(mapper(isFocused))
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        onFocusChange.set(mapper(hasFocus))
    }
}

fun <T : Any> View.onFocusChange(onFocusChange: CoroutineItem<T>, mapper: (Boolean) -> T) {
    onFocusChange.set(mapper(isFocused))
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        onFocusChange.set(mapper(hasFocus))
    }
}

fun View.onFocusChangeForceFalseOnClearFocus(onFocusChange: CoroutineBoolean) =
    onFocusChangeForceFalseOnClearFocus(onFocusChange) { it }

fun View.onFocusChangeForceFalseOnClearFocus(onFocusChange: CoroutineField<Boolean>) =
    onFocusChangeForceFalseOnClearFocus(onFocusChange) { it }

fun View.onFocusChangeForceFalseOnClearFocus(onFocusChange: CoroutineItem<Boolean>) =
    onFocusChangeForceFalseOnClearFocus(onFocusChange) { it }

fun <T : Any> View.onFocusChangeForceFalseOnClearFocus(
    onFocusChange: CoroutineField<T>,
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

fun <T : Any> View.onFocusChangeForceFalseOnClearFocus(
    onFocusChange: CoroutineItem<T>,
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

fun View.setTranslationY(translation: CoroutineField<Float>) =
    setTranslationY(translation.onlyPresent())

fun View.setTranslationY(translation: CoroutineItem<Float>) =
    setTranslationY(translation.observe())

fun View.setTranslationY(translation: CoroutineFloat) =
    setTranslationY(translation.observe())

fun View.setTranslationY(translation: Flow<Float>) =
    addSetter(translation) { translationY = it }

/**
 * Height
 */

@Suppress("ObjectLiteralToLambda", "DEPRECATION")
fun View.getHeight(field: CoroutineInt) {
    onGlobalLayoutDelegate.addOnGlobalLayoutCallback(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            field.set(measuredHeight)
        }
    })
}


/**
 * ON SCROLL
 */

fun <T : Any> View.onScroll(onScroll: CoroutineField<T>, mapper: (OnScroll) -> T) {
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

fun View.onScroll(onScroll: CoroutineField<OnScroll>) =
    onScroll(onScroll) { it }

/**
 * ON FLING
 */

fun <T : Any> View.onFling(onFling: CoroutineField<T>, mapper: (OnFling) -> T) {
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

fun View.onFling(onFling: CoroutineField<OnFling>) =
    onFling(onFling) { it }

/**
 * ON DOWN
 */

fun <T : Any> View.onDown(onDown: CoroutineField<T>, mapper: (Optional<MotionEvent>) -> T) {
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

fun View.onDown(onDown: CoroutineField<Optional<MotionEvent>>) =
    onDown(onDown) { it }

/**
 * ON SINGLE TAP UP
 */

fun <T : Any> View.onSingleTapUp(
    onSingleTapUp: CoroutineField<T>,
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
    setClickable()
}

fun View.onSingleTapUp(onSingleTapUp: CoroutineField<Optional<MotionEvent>>) =
    onSingleTapUp(onSingleTapUp) { it }

/**
 * ON SHOW PRESS
 */

fun <T : Any> View.onShowPress(
    onShowPress: CoroutineField<T>,
    mapper: (Optional<MotionEvent>) -> T
) {
    gestureDetectorCallback.addOnShowPressCallback(object :
        GestureDetector.SimpleOnGestureListener() {
        override fun onShowPress(e: MotionEvent?) {
            onShowPress.set(mapper(Optional.of(e)))
        }
    })
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

fun View.onShowPress(onShowPress: CoroutineField<Optional<MotionEvent>>) =
    onShowPress(onShowPress) { it }

/**
 * ON LONG PRESS
 */

fun <T : Any> View.onLongPress(
    onLongPress: CoroutineField<T>,
    mapper: (Optional<MotionEvent>) -> T
) {
    gestureDetectorCallback.addOnShowPressCallback(object :
        GestureDetector.SimpleOnGestureListener() {
        override fun onShowPress(e: MotionEvent?) {
            onLongPress.set(mapper(Optional.of(e)))
        }
    })
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    setClickable()
}

fun View.onLongPress(onLongPress: CoroutineField<Optional<MotionEvent>>) =
    onLongPress(onLongPress) { it }