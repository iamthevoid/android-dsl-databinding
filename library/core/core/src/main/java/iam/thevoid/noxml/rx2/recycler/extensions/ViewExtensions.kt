package iam.thevoid.noxml.rx2.recycler.extensions

import android.view.GestureDetector
import android.view.View
import iam.thevoid.noxml.R
import iam.thevoid.noxml.binding.ObserveListener
import iam.thevoid.noxml.delegate.OnGestureDelegate
import iam.thevoid.noxml.delegate.OnGlobalLayoutListenerDelegate

typealias Closure<T> = ((T) -> Unit) -> Unit

/**
 * =============== BASE ==============
 */

/**
 * Basics.
 * @observeListener is the child of @see#View.OnAttachStateChangeListener and the core of reactive UI.
 *
 * Stores all flowables and subscribe on it changes on attach to window and unsubscribe on detach from window.
 */

val View.observeListener: ObserveListener
    get() = ((getTag(R.id.listener) as? ObserveListener)
        ?: ObserveListener().also {
            setTag(R.id.listener, it)
            addOnAttachStateChangeListener(it)
        })

fun View.fakeAttachToWindow() =
    observeListener.onViewAttachedToWindow(this)

fun View.fakeDetachFromWindow() =
    observeListener.onViewDetachedFromWindow(this)

/**
 * On global layout
 */

val View.onGlobalLayoutDelegate
    get() = (getTag(R.id.onGlobalLayoutDelegate) as? OnGlobalLayoutListenerDelegate)
        ?: OnGlobalLayoutListenerDelegate().also {
            viewTreeObserver.addOnGlobalLayoutListener(it)
            setTag(R.id.onGlobalLayoutDelegate, it)
        }

/**
 * Gesture
 */

val View.gestureDetector
    get() = (getTag(R.id.gestureDetector) as? GestureDetector) ?: GestureDetector(
        context,
        gestureDetectorCallback
    )

val View.gestureDetectorCallback: OnGestureDelegate
    get() = (getTag(R.id.gestureDetectorCallback) as? OnGestureDelegate)
        ?: OnGestureDelegate().also { setTag(R.id.gestureDetectorCallback, it) }