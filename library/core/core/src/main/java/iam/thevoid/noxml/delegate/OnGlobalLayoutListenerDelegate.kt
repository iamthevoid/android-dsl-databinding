package iam.thevoid.noxml.delegate

import android.view.ViewTreeObserver

class OnGlobalLayoutListenerDelegate : ViewTreeObserver.OnGlobalLayoutListener {

    private val onGlobalLayoutCallback = mutableListOf<ViewTreeObserver.OnGlobalLayoutListener>()

    fun addOnGlobalLayoutCallback(callback: ViewTreeObserver.OnGlobalLayoutListener) =
        onGlobalLayoutCallback.add(callback)

    fun removeOnGlobalLayoutCallback(callback: ViewTreeObserver.OnGlobalLayoutListener) =
        onGlobalLayoutCallback.remove(callback)

    override fun onGlobalLayout() {
        onGlobalLayoutCallback.forEach(ViewTreeObserver.OnGlobalLayoutListener::onGlobalLayout)
    }
}
