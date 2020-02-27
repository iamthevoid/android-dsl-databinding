package iam.thevoid.noxml.demo.ui.mvvm.scroll

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class OversizeBehavior : AppBarLayout.ScrollingViewBehavior() {

    val tempRect = Rect()

    override fun onRequestChildRectangleOnScreen(
        parent: CoordinatorLayout,
        child: View,
        rectangle: Rect,
        immediate: Boolean
    ): Boolean {
        val header = findAppBar(parent.getDependencies(child))
        if (header != null) {
            // Offset the rect by the child's left/top
            rectangle.offset(child.left, child.top)

            val parentRect = tempRect
            parentRect.set(0, 0, parent.width, parent.height)

            if (!parentRect.contains(rectangle)) {
                // If the rectangle can not be fully seen the visible bounds, collapse
                // the AppBarLayout
                header.setExpanded(false, !immediate)
                return true
            } else {
                Log.d("OversizeBehavior", "fds")
            }
        }
        return false
    }

    fun findAppBar(views: List<View>): AppBarLayout? {
        var i = 0
        val z = views.size
        while (i < z) {
            val view = views[i]
            if (view is AppBarLayout) {
                return view
            }
            i++
        }
        return null
    }

}