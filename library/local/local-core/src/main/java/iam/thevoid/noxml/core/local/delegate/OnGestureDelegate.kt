package iam.thevoid.noxml.core.local.delegate

import android.view.GestureDetector
import android.view.MotionEvent

class OnGestureDelegate : GestureDetector.OnGestureListener {

    private val onShowPressCallback by lazy { mutableListOf<GestureDetector.OnGestureListener>() }
    private val onSingleTapUpCallback by lazy { mutableListOf<GestureDetector.OnGestureListener>() }
    private val onDownCallback by lazy { mutableListOf<GestureDetector.OnGestureListener>() }
    private val onFlingCallback by lazy { mutableListOf<GestureDetector.OnGestureListener>() }
    private val onScrollCallback by lazy { mutableListOf<GestureDetector.OnGestureListener>() }
    private val onLongPressCallback by lazy { mutableListOf<GestureDetector.OnGestureListener>() }


    fun addOnShowPressCallback(callback: GestureDetector.OnGestureListener) {
        onShowPressCallback.add(callback)
    }

    fun addOnSingleTapUpCallback(callback: GestureDetector.OnGestureListener) {
        onSingleTapUpCallback.add(callback)
    }

    fun addOnDownCallback(callback: GestureDetector.OnGestureListener) {
        onDownCallback.add(callback)
    }

    fun addOnFlingCallback(callback: GestureDetector.OnGestureListener) {
        onFlingCallback.add(callback)
    }

    fun addOnScrollCallback(callback: GestureDetector.OnGestureListener) {
        onScrollCallback.add(callback)
    }

    fun addOnLongPressCallback(callback: GestureDetector.OnGestureListener) {
        onLongPressCallback.add(callback)
    }


    fun removeOnShowPressCallback(callback: GestureDetector.OnGestureListener) {
        onShowPressCallback.remove(callback)
    }

    fun removeOnSingleTapUpCallback(callback: GestureDetector.OnGestureListener) {
        onSingleTapUpCallback.remove(callback)
    }

    fun removeOnDownCallback(callback: GestureDetector.OnGestureListener) {
        onDownCallback.remove(callback)
    }

    fun removeOnFlingCallback(callback: GestureDetector.OnGestureListener) {
        onFlingCallback.remove(callback)
    }

    fun removeOnScrollCallback(callback: GestureDetector.OnGestureListener) {
        onScrollCallback.remove(callback)
    }

    fun removeOnLongPressCallback(callback: GestureDetector.OnGestureListener) {
        onLongPressCallback.remove(callback)
    }


    override fun onShowPress(e: MotionEvent?) =
        onShowPressCallback.forEach { it.onShowPress(e) }

    override fun onSingleTapUp(e: MotionEvent?): Boolean =
        onSingleTapUpCallback.map { it.onSingleTapUp(e) }.all { it }

    override fun onDown(e: MotionEvent?): Boolean =
        onDownCallback.map { it.onDown(e) }.all { it }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean =
        onFlingCallback.map { it.onFling(e1, e2, velocityX, velocityY) }.all { it }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean =
        onScrollCallback.map { it.onScroll(e1, e2, distanceX, distanceY) }.all { it }

    override fun onLongPress(e: MotionEvent?) =
        onLongPressCallback.forEach { it.onLongPress(e) }

}
