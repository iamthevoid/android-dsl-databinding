package iam.thevoid.noxml.demo.ui.mvvm.scroll

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import iam.thevoid.ae.setPaddings
import iam.thevoid.noxml.anko.rx.AnkoRxLayout
import org.jetbrains.anko.*
import iam.thevoid.noxml.rx.ext.setText

class IntItem(viewGroup: ViewGroup) : AnkoRxLayout<Integer>(viewGroup) {
    override fun createView(ui: AnkoContext<AnkoRxLayout<Integer>>): View =
        ui.textView {
            textColor = Color.BLACK
            setPaddings(dip(20))
            gravity = Gravity.CENTER_VERTICAL
            setText(item.map { "item ${it?.toInt()}" })
        }.apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, dip(48))
        }
}