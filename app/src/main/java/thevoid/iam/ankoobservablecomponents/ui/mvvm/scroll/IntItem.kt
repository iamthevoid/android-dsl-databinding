package thevoid.iam.ankoobservablecomponents.ui.mvvm.scroll

import android.view.View
import android.view.ViewGroup
import iam.thevoid.ankoviews.widget.adapter.AnkoLayout
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textView
import thevoid.iam.components.widget.ext.setText

class IntItem(viewGroup: ViewGroup) : AnkoLayout<Integer>(viewGroup) {
    override fun createView(ui: AnkoContext<AnkoLayout<Integer>>): View =
        ui.textView {
            setText(item.observe { "item ${it.elem?.toInt()}" })
        }.apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, dip(48))
        }
}