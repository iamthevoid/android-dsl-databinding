package iam.thevoid.noxml.recycler.local

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.e.orElse
import iam.thevoid.e.safe

class StartEndPaddingRecyclerDecoration(
    var start: Int = 0,
    var end: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.layoutManager?.also {
            val itemPosition = parent.getChildAdapterPosition(view)
            when (it) {
                is GridLayoutManager -> {
                    val spanCount = it.spanCount
                    val countAll = parent.adapter?.itemCount.safe()
                    val spacingFrom = countAll - (countAll % spanCount).orElse(spanCount)

                    when {
                        itemPosition < spanCount -> outRect.startSpacing(it)
                        itemPosition > spacingFrom -> outRect.endSpacing(it)
                        else -> outRect.setSpacing()
                    }
                }

                is LinearLayoutManager -> {
                    when (itemPosition) {
                        0 -> outRect.startSpacing(it)
                        parent.adapter?.itemCount.safe() - 1 -> outRect.endSpacing(it)
                        else -> outRect.setSpacing()
                    }
                }
            }
        }
    }

    private fun Rect.endSpacing(layoutManager: LinearLayoutManager) {
        when (layoutManager.orientation) {
            LinearLayoutManager.VERTICAL -> setSpacing(b = end)
            LinearLayoutManager.HORIZONTAL -> setSpacing(r = end)
        }
    }

    private fun Rect.startSpacing(layoutManager: LinearLayoutManager) {
        when (layoutManager.orientation) {
            LinearLayoutManager.VERTICAL -> setSpacing(t = start)
            LinearLayoutManager.HORIZONTAL -> setSpacing(l = start)
        }
    }

    private fun Rect.setSpacing(l: Int = 0, t: Int = 0, r: Int = 0, b: Int = 0) {
        left = l
        top = t
        right = r
        bottom = b
    }
}
