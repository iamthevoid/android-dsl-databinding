package iam.thevoid.noxml.adapters

import android.view.View

open class OnAttachStateChangeListenerAdapter  : View.OnAttachStateChangeListener {
    override fun onViewDetachedFromWindow(v: View?) = Unit
    override fun onViewAttachedToWindow(v: View?) = Unit
}