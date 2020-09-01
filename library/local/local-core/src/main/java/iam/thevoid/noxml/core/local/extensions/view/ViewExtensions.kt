package iam.thevoid.noxml.core.local.extensions.view

import android.view.GestureDetector
import android.view.View
import iam.thevoid.noxml.core.local.R
import iam.thevoid.noxml.core.local.binding.SettersCache
import iam.thevoid.noxml.core.local.delegate.OnGestureDelegate
import iam.thevoid.noxml.core.local.delegate.OnGlobalLayoutListenerDelegate

/**
 * Basics.
 * @observeListener is the child of @see#View.OnAttachStateChangeListener and the core of reactive UI.
 *
 * Stores all flowables and subscribe on it changes on attach to window and unsubscribe on detach from window.
 */

val View.settersCache: SettersCache
    get() = ((getTag(R.id.cache) as? SettersCache)
        ?: SettersCache().also {
            setTag(R.id.cache, it)
            addOnAttachStateChangeListener(it)
        })

fun View.bindDataImmediate() =
    settersCache.bindDataImmediate(this)

fun View.fakeAttachToWindow() =
    settersCache.onViewAttachedToWindow(this)

fun View.fakeDetachFromWindow() =
    settersCache.onViewDetachedFromWindow(this)

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