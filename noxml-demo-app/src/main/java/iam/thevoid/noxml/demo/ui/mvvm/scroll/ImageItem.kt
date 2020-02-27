package iam.thevoid.noxml.demo.ui.mvvm.scroll

import android.view.View
import android.view.ViewGroup
import iam.thevoid.noxml.anko.rx.AnkoRxLayout
import org.jetbrains.anko.*
import iam.thevoid.noxml.demo.util.setImageUrl

class ImageItem(viewGroup: ViewGroup) : AnkoRxLayout<String>(viewGroup) {
    override fun createView(ui: AnkoContext<AnkoRxLayout<String>>): View =
        ui.frameLayout {
            imageView {
                setImageUrl(item.onlyPresent())
            }.lparams(matchParent, wrapContent)
        }.apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        }
}