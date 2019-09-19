package iam.thevoid.recycler

import android.graphics.Canvas
import android.graphics.Rect
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HeaderItemDecoration(
    parent: RecyclerView,
    private val isHeader: (itemPosition: Int) -> Boolean
) : RecyclerView.ItemDecoration() {

    private var currentHeader: Pair<Int, RecyclerView.ViewHolder>? = null

    init {
        parent.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                // clear saved header as it can be outdated now
                currentHeader = null
            }
        })

        parent.doOnEachNextLayout {
            // clear saved layout as it may need layout update
            currentHeader = null
        }
        // handle click on sticky header
        parent.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(
                recyclerView: RecyclerView,
                motionEvent: MotionEvent
            ): Boolean {
                return if (motionEvent.action == ACTION_DOWN) {
                    motionEvent.y <= currentHeader?.second?.itemView?.bottom ?: 0
                } else false
            }
        })
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val topChild = parent.getChildAt(0) ?: return
        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        val headerView = getHeaderViewForItem(topChildPosition, parent) ?: return

        val contactPoint = headerView.bottom
        val childInContact = getChildInContact(parent, contactPoint) ?: return

        if (isHeader(parent.getChildAdapterPosition(childInContact))) {
            moveHeader(c, headerView, childInContact)
            return
        }

        drawHeader(c, headerView)
    }

    private fun getHeaderViewForItem(itemPosition: Int, parent: RecyclerView): View? {
        if (parent.adapter == null) {
            return null
        }
        val headerPosition = getHeaderPositionForItem(itemPosition)
        val headerType = parent.adapter?.getItemViewType(headerPosition) ?: return null
        // if match reuse viewHolder
        if (currentHeader?.first == headerPosition && currentHeader?.second?.itemViewType == headerType) {
            return currentHeader?.second?.itemView
        }

        val headerHolder = parent.adapter?.createViewHolder(parent, headerType)
        if (headerHolder != null) {
            parent.adapter?.onBindViewHolder(headerHolder, headerPosition)
            fixLayoutSize(parent, headerHolder.itemView)
            // save for next draw
            currentHeader = headerPosition to headerHolder
        }
        return headerHolder?.itemView
    }

    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0f, 0f)
        header.draw(c)
        c.restore()
    }

    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View) {
        c.save()
        c.translate(0f, (nextHeader.top - currentHeader.height).toFloat())
        currentHeader.draw(c)
        c.restore()
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val mBounds = Rect()
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            if (mBounds.bottom > contactPoint) {
                if (mBounds.top <= contactPoint) {
                    // This child overlaps the contactPoint
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }

    /**
     * Properly measures and layouts the top sticky header.
     *
     * @param parent ViewGroup: RecyclerView in this case.
     */
    private fun fixLayoutSize(parent: ViewGroup, view: View) {

        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec =
            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            view.layoutParams.width
        )
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )

        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun getHeaderPositionForItem(itemPosition: Int): Int {
        var headerPosition = 0
        var currentPosition = itemPosition
        do {
            if (isHeader(currentPosition)) {
                headerPosition = currentPosition
                break
            }
            currentPosition -= 1
        } while (currentPosition >= 0)
        return headerPosition
    }
}

inline fun View.doOnEachNextLayout(crossinline action: (view: View) -> Unit) {
    addOnLayoutChangeListener { view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
        action(view)
    }
}