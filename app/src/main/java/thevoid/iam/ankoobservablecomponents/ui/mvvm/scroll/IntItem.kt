package thevoid.iam.ankoobservablecomponents.ui.mvvm.scroll

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import iam.thevoid.ae.setPaddings
import iam.thevoid.ankoviews.widget.adapter.AnkoLayout
import org.jetbrains.anko.*
import thevoid.iam.components.widget.ext.setText

class IntItem(viewGroup: ViewGroup) : AnkoLayout<Integer>(viewGroup) {
    override fun createView(ui: AnkoContext<AnkoLayout<Integer>>): View =
        ui.textView {
            textColor = Color.BLACK
            setPaddings(dip(20))
            gravity = Gravity.CENTER_VERTICAL
            setText(item.observe { "item ${it.elem?.toInt()}" })
        }.apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, dip(48))
        }
}