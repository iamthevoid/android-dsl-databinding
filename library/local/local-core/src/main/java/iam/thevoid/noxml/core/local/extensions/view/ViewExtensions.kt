package iam.thevoid.noxml.core.local.extensions.view

import android.view.GestureDetector
import android.view.View
import iam.thevoid.noxml.core.local.R
import iam.thevoid.noxml.core.local.binding.SettersCache
import iam.thevoid.noxml.core.local.delegate.OnGestureDelegate
import iam.thevoid.noxml.core.local.delegate.OnGlobalLayoutListenerDelegate
import iam.thevoid.noxml.core.local.tools.lazyDelegate

/**
 * Basics.
 * @observeListener is the child of @see#View.OnAttachStateChangeListener and the core of reactive UI.
 *
 * Stores all flowables and subscribe on it changes on attach to window and unsubscribe on detach from window.
 */

val View.settersCache: SettersCache
    get() = lazyDelegate(R.id.cache, ::SettersCache, ::addOnAttachStateChangeListener)

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
    get() = lazyDelegate(
        R.id.onGlobalLayoutDelegate,
        ::OnGlobalLayoutListenerDelegate,
        viewTreeObserver::addOnGlobalLayoutListener
    )


/**
 * Gesture
 */

val View.gestureDetector
    get() = lazyDelegate(R.id.gestureDetector, { GestureDetector(context, gestureDetectorCallback) })

val View.gestureDetectorCallback: OnGestureDelegate
    get() = lazyDelegate(R.id.gestureDetectorCallback, ::OnGestureDelegate)